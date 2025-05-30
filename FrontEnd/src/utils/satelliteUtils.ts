import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader.js';
import { 
  SatelliteTelemetryData,
  SatelliteData
 } from '@/types/satellite';

  /**
    * Load the satellite model
    * @method loadSatelliteModel
    * @param {string} modelPath - Path to the GLTF model
    * @param {THREE.Mesh} placeholder - Placeholder mesh for the satellite
    * @param {THREE.Group} group - Group container for the satellite
    */
  export function loadSatelliteModel( modelPath: string, placeholder: THREE.Object3D, group: THREE.Group ) : void {
    const gltfLoader = new GLTFLoader();
    gltfLoader.load(
      modelPath,
      (gltf) => {
        group.remove(placeholder);
        const satelliteModel = gltf.scene;
        satelliteModel.scale.set(0.1, 0.1, 0.1);
        satelliteModel.traverse((child: any) => {
          if (child.isMesh) {
            child.castShadow = true;
            if (child.material) {
              child.material.emissive = new THREE.Color(0x555555);
            }
          }
        });
        group.add(satelliteModel);
      },
      
      (error) => {
        console.error('Error loading satellite model:', error);
      }
    );
  }

  /**
    * Calculate the satellite's orbital trajectory
    * @method calculateSatelliteOrbitalTrajectory
    * @param {number} perigeeAltitude - Perigee altitude in kilometers
    * @param {number} eccentricity - Eccentricity of the orbit
    * @param {number} inclination - Inclination in degrees
    * @param {number} longitudeOfAscendingNode - Longitude of ascending node in degrees
    * @param {number} argumentOfPeriapsis - Argument of periapsis in degrees
    * @param {number} earthRadius - Radius of the Earth in kilometers
    * @param {number} scaleFactor - Scale factor for the scene
    * @returns {Object} - Contains cartesian values, points3D, and periodSeconds
    */
  export function calculateSatelliteOrbitalTrajectory(
      perigeeAltitude: number,
      eccentricity: number,
      inclination: number,
      longitudeOfAscendingNode: number,
      argumentOfPeriapsis: number,
      earthRadius: number,
      scaleFactor: number
    ): { cartesianValues: number[], points3D: THREE.Vector3[], periodSeconds: number } {
      const perigeeRadius = earthRadius + perigeeAltitude;
      const semiMajorAxis = perigeeRadius / (1 - eccentricity);
      const semiMajorAxisMeters = semiMajorAxis * 1000;
      const GM = 3.986004418e14;
      const periodSeconds = 2 * Math.PI * Math.sqrt(Math.pow(semiMajorAxisMeters, 3) / GM);

      const numSamples = 500;
      const timeStepSeconds = periodSeconds / numSamples;

      const cartesianValues: number[] = [];
      const points3D: THREE.Vector3[] = [];

      for (let i = 0; i <= numSamples; i++) {
          const sampleTime = i * timeStepSeconds;
          
          const meanAnomaly = (i / numSamples) * 2 * Math.PI;

          //** --> Kepler's equation numerical solving for eccentric anomaly (E) <-- **//
          let eccentricAnomaly = meanAnomaly;
          for (let j = 0; j < 50; j++) {
              const nextEccentricAnomaly = meanAnomaly + eccentricity * Math.sin(eccentricAnomaly);
              if (Math.abs(nextEccentricAnomaly - eccentricAnomaly) < 1e-6) break;
              eccentricAnomaly = nextEccentricAnomaly;
          }

          const trueAnomaly = 2 * Math.atan2(
              Math.sqrt(1 + eccentricity) * Math.sin(eccentricAnomaly / 2),
              Math.sqrt(1 - eccentricity) * Math.cos(eccentricAnomaly / 2)
          );
          const radius = semiMajorAxisMeters * (1 - eccentricity * Math.cos(eccentricAnomaly));

          const xOrbital = radius * Math.cos(trueAnomaly);
          const yOrbital = radius * Math.sin(trueAnomaly);

          const inclinationRad = inclination * (Math.PI / 180);
          const lonAscNodeRad = longitudeOfAscendingNode * (Math.PI / 180);
          const argPeriapsisRad = argumentOfPeriapsis * (Math.PI / 180);

          const xTemp = xOrbital * Math.cos(argPeriapsisRad) - yOrbital * Math.sin(argPeriapsisRad);
          const yTemp = xOrbital * Math.sin(argPeriapsisRad) + yOrbital * Math.cos(argPeriapsisRad);

          const yFinal = yTemp * Math.cos(inclinationRad);
          const zFinal = yTemp * Math.sin(inclinationRad);

          const xFinal = xTemp * Math.cos(lonAscNodeRad) - yFinal * Math.sin(lonAscNodeRad);
          const yFinalRotated = xTemp * Math.sin(lonAscNodeRad) + yFinal * Math.cos(lonAscNodeRad);

          //** Scale for visualization **//
          const xScene = xFinal * scaleFactor / 1000;
          const yScene = yFinalRotated * scaleFactor / 1000;
          const zScene = zFinal * scaleFactor / 1000;

          cartesianValues.push(sampleTime, xScene, yScene, zScene);
          points3D.push(new THREE.Vector3(xScene, yScene, zScene));
      }

      return { cartesianValues, points3D, periodSeconds };
    }

  /**
    * Create the satellite's orbit path
    * @method createSatelliteOrbitPath
    * @param {Object} satellite - Satellite object
    */
  export function createSatelliteOrbitPath(satellite: any,scene:THREE.Scene,orbitPaths:any): void {
    const geometry = new THREE.BufferGeometry().setFromPoints(satellite.animationParams.orbitPoints);
    const material = new THREE.LineBasicMaterial({
      color: satellite.color,
      transparent: true,
      opacity: 0.8
    });
    
    const orbitPath = new THREE.Line(geometry, material);
    scene.add(orbitPath);
    satellite.orbitPath = orbitPath;
    orbitPaths.push(orbitPath);
  }

  /**
    * Update the satellite's position based on its orbital parameters
    * @method updateSatellitePosition
    * @param {Object} satellite - Satellite object
    */
  export function updateSatellitePosition(satellite: any): void {
    if (!satellite.animationParams.orbitPoints.length) return;
    
    const points = satellite.animationParams.orbitPoints;
    const totalPoints = points.length;
    const normalizedTime = (satellite.animationParams.currentTime % satellite.animationParams.periodSeconds) / satellite.animationParams.periodSeconds;
    const index = Math.floor(normalizedTime * (totalPoints - 1));
    const nextIndex = (index + 1) % totalPoints;
    
    const fraction = (normalizedTime * (totalPoints - 1)) - index;
    const position = new THREE.Vector3();
    position.lerpVectors(points[index], points[nextIndex], fraction);
    
    satellite.group.position.copy(position);
  }
  
  /**
    * Get the current telemetry data for the satellite
    * @method getCurrentSatelliteTelemetry
    * @param {Object} selectedSatellite - Selected satellite object
    * @returns {Array} - Array of telemetry data objects
    */
  export function getCurrentSatelliteTelemetry(
    selectedSatellite: SatelliteData
  ): SatelliteTelemetryData[] {
    if (!selectedSatellite.orbitalData) {
      throw new Error("Satellite has no orbital data");
    }
  
    const { cartesianValues, points3D, periodSeconds } = selectedSatellite.orbitalData;
    const now = Date.now();
    const tleEpoch = new Date(selectedSatellite.tleEpoch);
  
    const normalizedTime = (now / 1000 % periodSeconds) / periodSeconds;
    const totalPoints = points3D.length;
    const index = Math.floor(normalizedTime * (totalPoints - 1));
    const nextIndex = (index + 1) % totalPoints;
  
    const fraction = (normalizedTime * (totalPoints - 1)) - index;
    const position = new THREE.Vector3().lerpVectors(points3D[index], points3D[nextIndex], fraction);
  
    const timeStep = periodSeconds / totalPoints;
    const velocity = new THREE.Vector3()
      .subVectors(points3D[nextIndex], points3D[index])
      .divideScalar(timeStep);
  
    const earthRadius = 6371;
    const lat = Math.atan2(position.z, Math.sqrt(position.x ** 2 + position.y ** 2)) * (180 / Math.PI);
    const lon = Math.atan2(position.y, position.x) * (180 / Math.PI);
    const alt = position.length() - earthRadius;
  
    const semiMajorAxis = new THREE.Vector3(cartesianValues[1], cartesianValues[2], cartesianValues[3]).length();
    const eccentricity = selectedSatellite.eccentricity || 0;
  
    const tleAgeDays = (now - tleEpoch.getTime()) / (1000 * 60 * 60 * 24);
  
    return [
      { property: "TLE Age [days]", value: tleAgeDays.toFixed(12) },
      { property: "Latitude [deg]", value: lat.toFixed(12) },
      { property: "Longitude [deg]", value: lon.toFixed(12) },
      { property: "Altitude [m]", value: (alt * 1000).toFixed(6) },
      { property: "x [m]", value: (position.x * 1000).toFixed(6) },
      { property: "y [m]", value: (position.y * 1000).toFixed(6) },
      { property: "z [m]", value: (position.z * 1000).toFixed(6) },
      { property: "dx/dt [m/s]", value: (velocity.x * 1000).toFixed(6) },
      { property: "dy/dt [m/s]", value: (velocity.y * 1000).toFixed(6) },
      { property: "dz/dt [m/s]", value: (velocity.z * 1000).toFixed(6) },
      { property: "Semimajor axis (a) [m]", value: (semiMajorAxis * 1000).toFixed(6) },
      { property: "Eccentricity (e)", value: eccentricity.toFixed(12) },
      { property: "Inclination (i) [deg]", value: selectedSatellite.inclination.toFixed(12) },
      { property: "RAAN [deg]", value: selectedSatellite.longitudeOfAscendingNode.toFixed(12) },
    ];
  }