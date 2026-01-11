import React, { useEffect, useRef, useState } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { useSatelliteContext } from '@/contexts/SatelliteContext';
import * as THREE from 'three';
import TornadoAlert from './TornadoAlert';
import { earthRadiusSceneUnits, earthRadius } from '@/utils/constants';

const LeafletEarthMap = () => {
  const mapRef = useRef<L.Map | null>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const satelliteMarkersRef = useRef<Map<string, L.CircleMarker>>(new Map());
  const sensorCirclesRef = useRef<Map<string, L.Circle>>(new Map());
  const satelliteTrailsRef = useRef<Map<string, L.Polyline>>(new Map());
  const satelliteLabelsRef = useRef<Map<string, L.Marker>>(new Map());
  const tornadoMarkersRef = useRef<L.Marker[]>([]);
  const selectedMarkerRef = useRef<L.CircleMarker | null>(null);
  
  const { satellites, selectedSatellite } = useSatelliteContext();
  const [animationTime, setAnimationTime] = useState(0);

  // Tornado locations (matching 3D viewer)
  const tornadoLocations = [
    { lat: 33.984376, lng: 106.867138, name: "Morocco Tornado", severity: 'warning' as const },
    { lat: 42, lng: 22, name: "Denver Tornado", severity: 'warning' as const },
    { lat: 35, lng: -63, name: "Denver Tornado", severity: 'watch' as const },
    { lat: 43, lng: -153, name: "Denver Tornado", severity: 'watch' as const },
  ]

  // Convert 3D position to lat/lng - corrected to match 3D viewer coordinate system
  const position3DToLatLng = (position: THREE.Vector3): [number, number] => {
    const radius = position.length();
    // Convert from 3D viewer coordinates to geographic coordinates
    const lat = Math.asin(position.y / radius) * (180 / Math.PI);
    const lon = Math.atan2(position.z, -position.x) * (180 / Math.PI);
    return [lat, lon];
  };

  // Calculate current satellite position using the same timing as 3D viewer
  const getCurrentSatellitePosition = (satellite: any, currentTime: number) => {
    if (!satellite.orbitalData?.points3D || !satellite.orbitalData?.periodSeconds) return null;
    const speedMultiplier = satellite.speedMultiplier ?? 1;
    const adjustedTime = (currentTime * speedMultiplier) % satellite.orbitalData.periodSeconds;
    const normalizedTime = adjustedTime / satellite.orbitalData.periodSeconds;
    const index = Math.floor(normalizedTime * (satellite.orbitalData.points3D.length - 1));
    const nextIndex = (index + 1) % satellite.orbitalData.points3D.length;
    const fraction = (normalizedTime * (satellite.orbitalData.points3D.length - 1)) - index;
    const currentPos = new THREE.Vector3();
    currentPos.lerpVectors(satellite.orbitalData.points3D[index], satellite.orbitalData.points3D[nextIndex], fraction);
    return currentPos;
  };

  // Calculate dynamic sensor radius based on altitude (matching 3D behavior more accurately)
  const calculateDynamicSensorRadius = (satellite: any, currentPos: THREE.Vector3) => {
    if (!satellite.sensorData?.active) return null;
    // Use the same parameters as 3D, but convert scene units to km
    const sceneToKm = earthRadius / earthRadiusSceneUnits; // 6371 / 4
    const earthRadiusKm = earthRadius; // 6371
    // Get current altitude in km (distance from Earth's center minus Earth's radius)
    const currentAltitude = Math.abs(currentPos.length() - earthRadiusKm);
    // Calculate constant cone half-angle from max values (converted to km)
    const maxBaseRadiusKm = (satellite.maxSensorBaseRadius) * sceneToKm / 5;
    const maxHeightKm = (satellite.maxSensorHeight) * sceneToKm;
    const coneAngle = Math.atan(maxBaseRadiusKm / maxHeightKm); // constant
    // Use current altitude for intersection calculation
    const h = currentAltitude;
    const R = earthRadiusKm;
    const theta = coneAngle;
    if (h === 0) {
      return null;
    }
    const numerator = R * Math.sin(theta);
    const denominator = 1 - (R / (h + R)) * Math.cos(theta);
    const groundRadius = denominator !== 0 ? numerator / denominator : 0;
    return groundRadius;
  };

  // Create custom label icon
  const createLabelIcon = (name: string, color: string) => {
    return L.divIcon({
      html: `<div style="color: ${color}; font-weight: bold; text-shadow: 1px 1px 2px rgba(0,0,0,0.8); font-size: 12px; white-space: nowrap;">${name}</div>`,
      className: 'satellite-label',
      iconSize: [100, 20],
      iconAnchor: [50, 25]
    });
  };

  // Create tornado icon
  const createTornadoIcon = (severity: 'warning' | 'watch') => {
    const color = severity === 'warning' ? '#ef4444' : '#f97316';
    return L.divIcon({
      html: `<div style="
        background: ${color}; 
        border: 2px solid white; 
        border-radius: 50%; 
        width: 20px; 
        height: 20px; 
        position: relative;
        box-shadow: 0 0 10px rgba(0,0,0,0.3);
      ">
        <div style="
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          color: white;
          font-size: 12px;
          font-weight: bold;
        ">🌪️</div>
      </div>`,
      className: 'tornado-marker',
      iconSize: [20, 20],
      iconAnchor: [10, 10]
    });
  };

  // Initialize map
  useEffect(() => {
    if (!containerRef.current || mapRef.current) return;

    mapRef.current = L.map(containerRef.current, {
      center: [0, 0],
      zoom: 2,
      worldCopyJump: true,
      maxBounds: [[-90, -180], [90, 180]],
      maxBoundsViscosity: 1.0
    });

    // Add satellite imagery tiles with natural Earth colors
    L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
      attribution: '© Esri, Maxar, Earthstar Geographics',
      maxZoom: 18,
      noWrap: false
    }).addTo(mapRef.current);

    // Add tornado markers
    tornadoLocations.forEach(tornado => {
      const marker = L.marker([tornado.lat, tornado.lng], {
        icon: createTornadoIcon(tornado.severity)
      }).addTo(mapRef.current!);
      
      marker.bindPopup(`
        <div class="tornado-popup">
          <strong style="color: ${tornado.severity === 'warning' ? '#ef4444' : '#f97316'}">
            🌪️ ${tornado.name}
          </strong><br/>
          <small>Severity: ${tornado.severity.toUpperCase()}</small><br/>
          <small>Coordinates: ${tornado.lat.toFixed(4)}°, ${tornado.lng.toFixed(4)}°</small>
        </div>
      `);
      
      tornadoMarkersRef.current.push(marker);
    });

    return () => {
      if (mapRef.current) {
        mapRef.current.remove();
        mapRef.current = null;
      }
    };
  }, []);

  // Update satellites and sensors
  useEffect(() => {
    if (!mapRef.current) return;

    const updateSatellites = () => {
      const currentTime = Date.now() / 1000;
      setAnimationTime(currentTime);

      satellites.forEach(satellite => {
        console.log(`Processing satellite ${satellite.name}:`, {
          sensorData: satellite.sensorData,
          active: satellite.sensorData?.active,
          baseRadiusKm: satellite.sensorData?.baseRadiusKm
        });

        const currentPos = getCurrentSatellitePosition(satellite, currentTime);
        if (!currentPos) return;

        const [lat, lng] = position3DToLatLng(currentPos);
        const latLng = L.latLng(lat, lng);

        // Update or create satellite marker
        let marker = satelliteMarkersRef.current.get(satellite.id);
        if (!marker) {
          marker = L.circleMarker(latLng, {
            radius: 8,
            fillColor: satellite.color,
            color: satellite.color,
            weight: 2,
            opacity: 1,
            fillOpacity: 0.8
          }).addTo(mapRef.current!);
          
          // Add popup with satellite name
          marker.bindPopup(satellite.name);
          
          satelliteMarkersRef.current.set(satellite.id, marker);
        } else {
          marker.setLatLng(latLng);
        }

        // Update or create satellite label
        let label = satelliteLabelsRef.current.get(satellite.id);
        if (!label) {
          label = L.marker(latLng, {
            icon: createLabelIcon(satellite.name, satellite.color)
          }).addTo(mapRef.current!);
          
          satelliteLabelsRef.current.set(satellite.id, label);
        } else {
          label.setLatLng(latLng);
          label.setIcon(createLabelIcon(satellite.name, satellite.color));
        }

        // Handle sensor coverage circle with proper dynamic radius calculation
        if (satellite.sensorData?.active) {
          const dynamicRadius = calculateDynamicSensorRadius(satellite, currentPos);
          if (dynamicRadius && dynamicRadius > 0) {
            
            let sensorCircle = sensorCirclesRef.current.get(satellite.id);
            if (!sensorCircle) {
              sensorCircle = L.circle(latLng, {
                radius: dynamicRadius * 1000, // Convert km to meters for Leaflet
                fillColor: satellite.color,
                color: satellite.color,
                weight: 3,
                opacity: 0.9,
                fillOpacity: 0.15,
                dashArray: '8, 12' // Dashed border for better visibility
              }).addTo(mapRef.current!);
              
              // Add popup to sensor circle
              sensorCircle.bindPopup(`${satellite.name} - Sensor Coverage: ${Math.round(dynamicRadius)}km`);
              
              sensorCirclesRef.current.set(satellite.id, sensorCircle);
            } else {
              sensorCircle.setLatLng(latLng);
              sensorCircle.setRadius(dynamicRadius * 1000);
              // Update popup with current radius
              sensorCircle.getPopup()?.setContent(`${satellite.name} - Sensor Coverage: ${Math.round(dynamicRadius)}km`);
            }
          }
        } else {
          // Remove sensor circle if sensor is not active
          const existingCircle = sensorCirclesRef.current.get(satellite.id);
          if (existingCircle) {
            mapRef.current!.removeLayer(existingCircle);
            sensorCirclesRef.current.delete(satellite.id);
          }
        }

        // Create orbital trail
        if (satellite.orbitalData?.points3D && !satelliteTrailsRef.current.has(satellite.id)) {
          const trailPoints: [number, number][] = [];
          
          satellite.orbitalData.points3D.forEach((point: THREE.Vector3) => {
            const [trailLat, trailLng] = position3DToLatLng(point);
            // Only add valid lat/lng points
            if (trailLat >= -90 && trailLat <= 90 && trailLng >= -180 && trailLng <= 180) {
              trailPoints.push([trailLat, trailLng]);
            }
          });
          
          if (trailPoints.length > 0) {
            const trail = L.polyline(trailPoints, {
              color: satellite.color,
              weight: 2,
              opacity: 0.7
            }).addTo(mapRef.current!);
            
            satelliteTrailsRef.current.set(satellite.id, trail);
          }
        }
      });

      // Update selected satellite marker
      if (selectedMarkerRef.current) {
        mapRef.current!.removeLayer(selectedMarkerRef.current);
        selectedMarkerRef.current = null;
      }

      if (selectedSatellite) {
        const currentPos = getCurrentSatellitePosition(selectedSatellite, currentTime);
        if (currentPos) {
          const [lat, lng] = position3DToLatLng(currentPos);
          
          selectedMarkerRef.current = L.circleMarker([lat, lng], {
            radius: 15,
            fillColor: 'transparent',
            color: '#ffffff',
            weight: 3,
            opacity: 1,
            fillOpacity: 0
          }).addTo(mapRef.current!);
        }
      }

      // Clean up markers for removed satellites
      const currentSatelliteIds = new Set(satellites.map(s => s.id));
      for (const [id, marker] of satelliteMarkersRef.current.entries()) {
        if (!currentSatelliteIds.has(id)) {
          mapRef.current!.removeLayer(marker);
          satelliteMarkersRef.current.delete(id);
          
          const label = satelliteLabelsRef.current.get(id);
          if (label) {
            mapRef.current!.removeLayer(label);
            satelliteLabelsRef.current.delete(id);
          }
          
          const sensorCircle = sensorCirclesRef.current.get(id);
          if (sensorCircle) {
            mapRef.current!.removeLayer(sensorCircle);
            sensorCirclesRef.current.delete(id);
          }
          
          const trail = satelliteTrailsRef.current.get(id);
          if (trail) {
            mapRef.current!.removeLayer(trail);
            satelliteTrailsRef.current.delete(id);
          }
        }
      }
    };

    // Initial update
    updateSatellites();

    // Set up animation loop
    const animationId = setInterval(updateSatellites, 100); // Update every 100ms

    return () => {
      clearInterval(animationId);
    };
  }, [satellites, selectedSatellite]);

  return (
    <div className="w-full h-full relative">


      <div 
        ref={containerRef} 
        className="w-full h-full relative z-0"
        style={{ minHeight: '300px' }}
      />
    </div>
  );
};

export default LeafletEarthMap;
