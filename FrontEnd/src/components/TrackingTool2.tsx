
import React, { useState } from "react";
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

const SensorStatusContent = () => {
  const { satellites, selectedSatelliteId, setSelectedSatelliteId } = useSatelliteContext();
  const [filterType, setFilterType] = useState("all");

  const filteredSatellites = satellites.filter(satellite => {
    const isActive = ["sat-1", "sat-3"].includes(satellite.id);
    
    if (filterType === "all") return true;
    if (filterType === "active") return isActive;
    if (filterType === "inactive") return !isActive;
    return true;
  });

  const getSensorStatus = (satelliteId) => {
    return ["sat-1", "sat-3"].includes(satelliteId);
  };

  const getCoverageArea = (satellite) => {
    const baseRadius = satellite.maxSensorBaseRadius * 1000;
    return Math.PI * baseRadius * baseRadius;
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
        <Table className="satellite-table">
          <TableHeader>
            <TableRow className="border-b border-satellite-border bg-satellite-header">
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text">Satellite</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text">Status</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text">Height</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text">Coverage Area</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text">Coordinates</TableHead>
              <TableHead className="h-8 px-2 text-xs font-medium text-satellite-text">Base Radius</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filteredSatellites.map((satellite, index) => {
              const isActive = getSensorStatus(satellite.id);
              const isSelected = satellite.id === selectedSatelliteId;
              
              return (
                <TableRow 
                  key={satellite.id}
                  onClick={() => setSelectedSatelliteId(satellite.id)}
                  className={`cursor-pointer border-b border-satellite-border transition-colors
                    ${isSelected ? 'bg-primary/20' : index % 2 === 0 ? 'bg-satellite-dark/30' : ''}
                    hover:bg-satellite-accent/20`}
                >
                  <TableCell className="p-2 h-9 align-middle">
                    <div className="flex items-center gap-3">
                    <Satellite 
                    size={14} 
                      className="text-satellite-accent" 
                      style={{ color: isSelected ? satellite.color : '#FFFFFF' }} 
                    />
                    <span className="text-xs font-medium truncate">{satellite.name}</span>
                    </div>
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle">
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
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono">
                    {isActive ? `${Math.round(satellite.perigeeAltitude)} km` : "—"}
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono">
                    {isActive ? `${getCoverageArea(satellite).toFixed(2)} km²` : "—"}
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono">
                    {isActive && satellite.sensorData ? 
                      `${satellite.sensorData.baseCenterCoordinates.lat.toFixed(2)}°, ${satellite.sensorData.baseCenterCoordinates.long.toFixed(2)}°` : 
                      "—"
                    }
                  </TableCell>
                  <TableCell className="p-2 h-9 align-middle text-xs font-mono">
                    {isActive ? `${satellite.maxSensorBaseRadius.toFixed(1)} km` : "—"}
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
