import React, { useState, useEffect, useRef } from "react";
import SatelliteWindow from "./SatelliteWindow";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Label } from "@/components/ui/label";
import { useSatelliteContext } from "@/contexts/SatelliteContext";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Circle, Satellite, MapPin, Radar } from "lucide-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { getCurrentSatelliteTelemetry } from "@/utils/satelliteUtils";
import { earthRadius, earthRadiusSceneUnits } from "@/utils/constants";

const SensorStatusContent = () => {
  const { satellites, selectedSatelliteId, setSelectedSatelliteId } = useSatelliteContext();
  const [filterType, setFilterType] = useState("all");
  const [realtimeData, setRealtimeData] = useState({});
  const intervalRef = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    const updateData = () => {
      const data = {};
      satellites.forEach(sat => {
        try {
          if (sat.orbitalData && sat.orbitalData.points3D && sat.orbitalData.points3D.length > 0) {
            const telemetry = getCurrentSatelliteTelemetry(sat);
            // Get current position exactly like the 3D scene does
            const now = Date.now();
            const { points3D, periodSeconds } = sat.orbitalData;
            const normalizedTime = (now / 1000 % periodSeconds) / periodSeconds;
            const totalPoints = points3D.length;
            const index = Math.floor(normalizedTime * (totalPoints - 1));
            const nextIndex = (index + 1) % totalPoints;
            const fraction = (normalizedTime * (totalPoints - 1)) - index;
            const position = points3D[index].clone().lerp(points3D[nextIndex], fraction);
            
            // Exact same calculation as 3D updateSensorCone function
            const altitudeScene = position.length() - earthRadiusSceneUnits;
            const altitudeKm = (position.length() * earthRadius) / earthRadiusSceneUnits - earthRadius;
            
            // Exact same base radius calculation as 3D scene
            const maxSensorHeight = sat.maxSensorHeight || 2.3;
            const maxSensorBaseRadius = sat.maxSensorBaseRadius || 0.9;
            const baseRadiusScene = (altitudeScene / maxSensorHeight) * maxSensorBaseRadius;
            const baseRadiusKm = baseRadiusScene * (earthRadius / earthRadiusSceneUnits);
            const coverageAreaKm2 = Math.PI * baseRadiusKm * baseRadiusKm;
            
            // Telemetry for lat/lon
            const lat = parseFloat(`${telemetry.find(t => t.property === "Latitude [deg]")?.value ?? "0"}`);
            const lon = parseFloat(`${telemetry.find(t => t.property === "Longitude [deg]")?.value ?? "0"}`);
            data[sat.id] = { lat, lon, altitudeKm, baseRadiusKm, coverageAreaKm2 };
          }
        } catch (e) {
          console.error('Error updating satellite data:', e);
          data[sat.id] = null;
        }
      });
      setRealtimeData(data);
    };
    updateData();
    intervalRef.current = setInterval(updateData, 150);
    return () => {
      if (intervalRef.current) clearInterval(intervalRef.current);
    };
  }, [satellites]);

  const filteredSatellites = satellites.filter(satellite => {
    const isActive = ["sat-1", "sat-3", "sat-4"].includes(satellite.id);
    
    if (filterType === "all") return true;
    if (filterType === "active") return isActive;
    if (filterType === "inactive") return !isActive;
    return true;
  });

  const getSensorStatus = (satelliteId) => {
    return ["sat-1", "sat-3", "sat-4"].includes(satelliteId);
  };

  const getCoverageArea = (satellite) => {
    const baseRadius = satellite.sensorData?.baseRadiusKm;
    if (baseRadius) {
      return Math.PI * baseRadius * baseRadius;
    }
    return 0;
  };

  return (
    <div className="p-2 h-full flex flex-col space-y-3">
      <div className="bg-satellite-header/50 rounded p-2">
        <RadioGroup 
          value={filterType} 
          onValueChange={setFilterType} 
          className="flex space-x-4"
        >
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="all" id="all" />
            <Label htmlFor="all" className="text-xs">Show All</Label>
          </div>
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="active" id="active" />
            <Label htmlFor="active" className="text-xs">Active Only</Label>
          </div>
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="inactive" id="inactive" />
            <Label htmlFor="inactive" className="text-xs">Inactive Only</Label>
          </div>
        </RadioGroup>
      </div>
      
      <ScrollArea className="flex-1 h-0">
        <Table className="satellite-table w-full">
          <TableHeader>
            <TableRow className="border-b border-satellite-border bg-satellite-header">
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text w-20">Satellite</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text w-16">Status</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text w-20">Height</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text w-24">Coverage Area</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text w-28">Coordinates</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text w-20">Base Radius</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filteredSatellites.map((satellite, index) => {
              const isActive = getSensorStatus(satellite.id);
              const isSelected = satellite.id === selectedSatelliteId;
              const real = realtimeData[satellite.id];
              return (
                <TableRow 
                  key={satellite.id}
                  onClick={() => setSelectedSatelliteId(satellite.id)}
                  className={`cursor-pointer border-b border-satellite-border transition-colors
                    ${isSelected ? 'bg-primary/20' : index % 2 === 0 ? 'bg-satellite-dark/30' : ''}
                    hover:bg-satellite-accent/20`}
                >
                  <TableCell className="p-2 h-9 align-middle w-20">
                    <div className="flex items-center gap-3">
                    <Satellite 
                    size={14} 
                      className="text-satellite-accent" 
                      style={{ color: isSelected ? satellite.color : '#FFFFFF' }} 
                    />
                    <span className="text-xs font-medium truncate">{satellite.name}</span>
                    </div>
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle w-16">
                    <div className="flex items-center gap-2">
                      <Circle 
                        size={12} 
                        fill={isActive ? "#4CAF50" : "#FF0000"} 
                        color={"#FFFFFF"} 
                      />
                      <span 
                        className={`text-xs font-medium ${isActive ? "text-green-500" : "text-red-500"}`}
                      >
                        {isActive ? "Active" : "Inactive"}
                      </span>
                    </div>
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono w-20">
                    {isActive && real ? `${real.altitudeKm.toFixed(2)} km` : "—"}
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono w-24">
                    {isActive && real ? `${real.coverageAreaKm2.toFixed(2)} km²` : "—"}
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono w-28">
                    {isActive && real ?
                      `${real.lat.toFixed(2)}°, ${real.lon.toFixed(2)}°`
                      : "—"
                    }
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono w-20">
                    {isActive && real ? `${real.baseRadiusKm.toFixed(2)} km` : "—"}
                  </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </ScrollArea>
    </div>
  );
};

const TrackingTool2 = () => {
  const { selectedSatellite } = useSatelliteContext();
  
  return (
    <SatelliteWindow 
      title={`Tracking Tool 2 (${selectedSatellite ? selectedSatellite.name : "No Selection"})`} 
      className="col-span-2 row-span-2"
    >
      <Tabs defaultValue="sensor" className="h-full flex flex-col">
        <TabsList className="bg-satellite-header rounded-none border-b border-satellite-border">
          <TabsTrigger value="basic" className="text-xs flex gap-1 items-center">
            <Radar size={12} />
            <span>Basic</span>
          </TabsTrigger>
          <TabsTrigger value="sensor" className="text-xs flex gap-1 items-center">
            <MapPin size={12} />
            <span>Sensor Status</span>
          </TabsTrigger>
        </TabsList>
        <TabsContent value="basic" className="flex-1 p-2 space-y-4">
          <div className="bg-satellite-dark/50 p-3 rounded-md border border-satellite-border">
            <p className="text-sm">Basic tracking information for {selectedSatellite?.name || "No Selection"}</p>
          </div>
        </TabsContent>
        <TabsContent value="sensor" className="flex-1 m-0 p-0 data-[state=active]:h-full">
          <SensorStatusContent />
        </TabsContent>
      </Tabs>
    </SatelliteWindow>
  );
};

export default TrackingTool2;
