import React, { useState, useEffect, useRef } from "react";
import SatelliteWindow from "./SatelliteWindow";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useSatelliteContext } from "@/contexts/SatelliteContext";
import { propagateState, initializeSatelliteState, getSatelliteState } from "@/utils/polarCoordinates";

const PolarPlotContent = () => {
  const { selectedSatellite } = useSatelliteContext();
  const [trackPosition, setTrackPosition] = useState({ top: "50%", left: "50%" });
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const animationFrameRef = useRef<number>();
  const lastUpdateTimeRef = useRef<number>(Date.now());
  
  useEffect(() => {
    if (!selectedSatellite) return;
    
    const telemetry = selectedSatellite.telemetry || [];
    
    const initialPosition = {
      x: parseFloat(String(telemetry.find(t => String(t.property).includes('x [m]'))?.value || '0')),
      y: parseFloat(String(telemetry.find(t => String(t.property).includes('y [m]'))?.value || '0')),
      z: parseFloat(String(telemetry.find(t => String(t.property).includes('z [m]'))?.value || '0'))
    };
    
    const initialVelocity = {
      x: parseFloat(String(telemetry.find(t => String(t.property).includes('dx/dt'))?.value || '0.1')),
      y: parseFloat(String(telemetry.find(t => String(t.property).includes('dy/dt'))?.value || '0.1')),
      z: parseFloat(String(telemetry.find(t => String(t.property).includes('dz/dt'))?.value || '0.1'))
    };
    
    if (!getSatelliteState(selectedSatellite.id)) {
      initializeSatelliteState(selectedSatellite.id, initialPosition, initialVelocity);
    }
    
    if (canvasRef.current) {
      const ctx = canvasRef.current.getContext('2d');
      if (ctx) {
        ctx.clearRect(0, 0, canvasRef.current.width, canvasRef.current.height);
        drawPolarGrid(ctx, selectedSatellite.color || '#00ffff');
        
        const state = getSatelliteState(selectedSatellite.id);
        if (state && state.trajectoryPoints.length > 0) {
          drawTrajectory(ctx, state.trajectoryPoints, selectedSatellite.color || '#00ffff');
          
          const lastPoint = state.trajectoryPoints[state.trajectoryPoints.length - 1];
          drawSatellitePosition(ctx, lastPoint, selectedSatellite.color || '#00ffff');
          
          setTrackPosition({
            top: `${lastPoint.y * 100}%`,
            left: `${lastPoint.x * 100}%`
          });
        }
      }
    }
    
    lastUpdateTimeRef.current = Date.now();
    
    console.log(`Initialized tracking for satellite: ${selectedSatellite.id}`);
  }, [selectedSatellite]);
  
  const drawPolarGrid = (ctx: CanvasRenderingContext2D, accentColor: string) => {
    const width = ctx.canvas.width;
    const height = ctx.canvas.height;
    const center = { x: width / 2, y: height / 2 };
    const maxRadius = Math.min(width, height) / 2 - 20;
    
    ctx.clearRect(0, 0, width, height);
    
    for (let i = 1; i <= 3; i++) {
      const radius = maxRadius * (i / 3);
      ctx.beginPath();
      ctx.arc(center.x, center.y, radius, 0, 2 * Math.PI);
      ctx.strokeStyle = '#2a3a4a';
      ctx.lineWidth = 1;
      ctx.stroke();
      
      const elevationAngle = 90 - (i * 30);
      ctx.fillStyle = '#6b7280';
      ctx.font = '10px sans-serif';
      ctx.textAlign = 'right';
      ctx.fillText(`${elevationAngle}°`, center.x - radius - 5, center.y);
    }
    
    ctx.beginPath();
    for (let i = 0; i < 8; i++) {
      const angle = (i * Math.PI) / 4;
      ctx.moveTo(center.x, center.y);
      ctx.lineTo(
        center.x + maxRadius * Math.cos(angle),
        center.y + maxRadius * Math.sin(angle)
      );
    }
    ctx.strokeStyle = '#2a3a4a';
    ctx.lineWidth = 1;
    ctx.stroke();
    
    const directions = ['N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW'];
    directions.forEach((dir, i) => {
      const angle = (i * Math.PI) / 4;
      const x = center.x + (maxRadius + 15) * Math.cos(angle);
      const y = center.y + (maxRadius + 15) * Math.sin(angle);
      
      ctx.fillStyle = '#6b7280';
      ctx.font = '10px sans-serif';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(dir, x, y);
    });
    
    ctx.beginPath();
    ctx.arc(center.x, center.y, maxRadius, 0, 2 * Math.PI);
    ctx.strokeStyle = '#4b5563';
    ctx.lineWidth = 2;
    ctx.stroke();
    
    ctx.fillStyle = '#d1d5db';
    ctx.font = '12px sans-serif';
    ctx.textAlign = 'center';
    ctx.fillText('Satellite Polar Plot', center.x, 20);
  };
  
  const drawTrajectory = (
    ctx: CanvasRenderingContext2D, 
    points: { x: number; y: number }[],
    color: string
  ) => {
    if (points.length < 2) return;
    
    const width = ctx.canvas.width;
    const height = ctx.canvas.height;
    
    ctx.beginPath();
    
    const firstPoint = points[0];
    ctx.moveTo(firstPoint.x * width, firstPoint.y * height);
    
    for (let i = 1; i < points.length; i++) {
      const point = points[i];
      ctx.lineTo(point.x * width, point.y * height);
    }
    
    ctx.strokeStyle = color;
    ctx.lineWidth = 1.5;
    ctx.stroke();
  };
  
  const drawSatellitePosition = (
    ctx: CanvasRenderingContext2D, 
    point: { x: number; y: number },
    color: string
  ) => {
    const width = ctx.canvas.width;
    const height = ctx.canvas.height;
    
    const x = point.x * width;
    const y = point.y * height;
    
    const now = Date.now();
    const pulseFactor = 1 + 0.2 * Math.sin(now / 500);
    
    ctx.beginPath();
    ctx.arc(x, y, 6 * pulseFactor, 0, 2 * Math.PI);
    ctx.fillStyle = `${color}33`;
    ctx.fill();
    
    ctx.beginPath();
    ctx.arc(x, y, 4 * pulseFactor, 0, 2 * Math.PI);
    ctx.fillStyle = `${color}66`;
    ctx.fill();
    
    ctx.beginPath();
    ctx.arc(x, y, 2 * pulseFactor, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
  };
  
  useEffect(() => {
    if (!selectedSatellite || !canvasRef.current) return;
    
    const updatePosition = () => {
      const ctx = canvasRef.current?.getContext('2d');
      if (!ctx) return;
      
      const currentTime = Date.now();
      const state = getSatelliteState(selectedSatellite.id);
      
      if (!state) return;
      
      const deltaTime = (currentTime - state.lastUpdateTime) / 1000;
      
      const result = propagateState(
        state.position,
        state.velocity,
        deltaTime * (selectedSatellite.speedMultiplier || 1),
        selectedSatellite.id
      );
      
      setTrackPosition({ 
        top: `${result.plotPoint.y * 100}%`, 
        left: `${result.plotPoint.x * 100}%` 
      });
      
      drawPolarGrid(ctx, selectedSatellite.color || '#00ffff');
      
      if (result.trajectoryPoints && result.trajectoryPoints.length > 0) {
        drawTrajectory(ctx, result.trajectoryPoints, selectedSatellite.color || '#00ffff');
        drawSatellitePosition(ctx, result.plotPoint, selectedSatellite.color || '#00ffff');
      }
      
      ctx.fillStyle = '#ffffff';
      ctx.font = '10px monospace';
      ctx.textAlign = 'left';
      ctx.fillText(`Az: ${result.polar.azimuth.toFixed(1)}°`, 10, ctx.canvas.height - 40);
      ctx.fillText(`El: ${result.polar.elevation.toFixed(1)}°`, 10, ctx.canvas.height - 25);
      ctx.fillText(`R: ${result.polar.radius.toFixed(1)} km`, 10, ctx.canvas.height - 10);
      
      animationFrameRef.current = requestAnimationFrame(updatePosition);
    };
    
    updatePosition();
    
    return () => {
      if (animationFrameRef.current) {
        cancelAnimationFrame(animationFrameRef.current);
      }
    };
  }, [selectedSatellite]);
  
  useEffect(() => {
    const updateCanvasSize = () => {
      if (canvasRef.current) {
        const container = canvasRef.current.parentElement;
        if (container) {
          canvasRef.current.width = container.clientWidth;
          canvasRef.current.height = container.clientHeight;
          
          const ctx = canvasRef.current.getContext('2d');
          if (ctx && selectedSatellite) {
            drawPolarGrid(ctx, selectedSatellite.color || '#00ffff');
            
            const state = getSatelliteState(selectedSatellite.id);
            if (state && state.trajectoryPoints.length > 0) {
              drawTrajectory(
                ctx, 
                state.trajectoryPoints,
                selectedSatellite.color || '#00ffff'
              );
              
              const lastPoint = state.trajectoryPoints[state.trajectoryPoints.length - 1];
              drawSatellitePosition(ctx, lastPoint, selectedSatellite.color || '#00ffff');
            }
          }
        }
      }
    };
    
    updateCanvasSize();
    window.addEventListener('resize', updateCanvasSize);
    
    return () => {
      window.removeEventListener('resize', updateCanvasSize);
    };
  }, [selectedSatellite]);
  
  if (!selectedSatellite) {
    return (
      <div className="h-full flex items-center justify-center">
        <p className="text-sm text-gray-500">No satellite selected</p>
      </div>
    );
  }
  
  return (
    <div className="relative h-full w-full bg-black overflow-hidden">
      <canvas ref={canvasRef} className="absolute inset-0 z-0" />
      
      <div className="absolute bottom-2 left-2 text-xs text-gray-400 z-10 bg-black/50 px-2 py-1 rounded">
        Satellite: {selectedSatellite.name}
      </div>
    </div>
  );
};

const BasicInfoContent = () => {
  const { selectedSatellite } = useSatelliteContext();
  
  if (!selectedSatellite) {
    return (
      <div className="h-full flex items-center justify-center">
        <p className="text-sm text-gray-500">No satellite selected</p>
      </div>
    );
  }
  
  return (
    <div className="p-4 space-y-4">
      <div className="space-y-2">
        <h3 className="text-sm font-medium text-satellite-highlight">Orbital Parameters</h3>
        <div className="grid grid-cols-2 gap-2 text-xs">
          <div className="space-y-1">
            <div className="flex justify-between">
              <span className="text-gray-400">Perigee:</span>
              <span className="text-satellite-text">{selectedSatellite.perigeeAltitude} km</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-400">Eccentricity:</span>
              <span className="text-satellite-text">{selectedSatellite.eccentricity}</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-400">Inclination:</span>
              <span className="text-satellite-text">{selectedSatellite.inclination}°</span>
            </div>
          </div>
          <div className="space-y-1">
            <div className="flex justify-between">
              <span className="text-gray-400">RAAN:</span>
              <span className="text-satellite-text">{selectedSatellite.longitudeOfAscendingNode}°</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-400">Arg. Periapsis:</span>
              <span className="text-satellite-text">{selectedSatellite.argumentOfPeriapsis}°</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const PassPredictionsContent = () => {
  const { selectedSatellite } = useSatelliteContext();
  
  if (!selectedSatellite) {
    return (
      <div className="h-full flex items-center justify-center">
        <p className="text-sm text-gray-500">No satellite selected</p>
      </div>
    );
  }
  
  const generatePassPredictions = () => {
    const now = new Date();
    const predictions = [];
    
    for (let i = 0; i < 5; i++) {
      const passTime = new Date(now.getTime() + (i + 1) * 90 * 60 * 1000);
      const duration = Math.floor(5 + Math.random() * 15);
      const maxElevation = Math.floor(20 + Math.random() * 70);
      
      predictions.push({
        time: passTime,
        duration,
        maxElevation
      });
    }
    
    return predictions;
  };
  
  const passPredictions = generatePassPredictions();
  
  return (
    <div className="p-2">
      <h3 className="text-sm font-medium text-satellite-highlight mb-2">Upcoming Passes</h3>
      <div className="space-y-2">
        {passPredictions.map((pass, index) => (
          <div key={index} className="bg-satellite-dark-accent/30 p-2 rounded text-xs">
            <div className="flex justify-between">
              <span className="text-gray-300">{pass.time.toLocaleTimeString()}</span>
              <span className="text-satellite-accent">{pass.time.toLocaleDateString()}</span>
            </div>
            <div className="flex justify-between mt-1">
              <span className="text-gray-400">Duration:</span>
              <span className="text-white">{pass.duration} min</span>
            </div>
            <div className="flex justify-between">
              <span className="text-gray-400">Max Elevation:</span>
              <span className="text-white">{pass.maxElevation}°</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

const TrackingTool1 = () => {
  const { selectedSatellite } = useSatelliteContext();
  
  return (
    <SatelliteWindow 
      title={`Tracking Tool 1 (${selectedSatellite ? selectedSatellite.name : "No Selection"})`} 
      className="col-span-2 row-span-2"
    >
      <Tabs defaultValue="polar" className="h-full flex flex-col">
        <TabsList className="bg-satellite-header rounded-none border-b border-satellite-border">
          <TabsTrigger value="basic" className="text-xs">Basic</TabsTrigger>
          <TabsTrigger value="polar" className="text-xs">Polar Plot</TabsTrigger>
          <TabsTrigger value="pass" className="text-xs">Pass Predictions</TabsTrigger>
        </TabsList>
        <TabsContent value="basic" className="flex-1 p-0 overflow-auto">
          <BasicInfoContent />
        </TabsContent>
        <TabsContent value="polar" className="flex-1 m-0 p-0 data-[state=active]:h-full">
          <PolarPlotContent />
        </TabsContent>
        <TabsContent value="pass" className="flex-1 p-0 overflow-auto">
          <PassPredictionsContent />
        </TabsContent>
      </Tabs>
    </SatelliteWindow>
  );
};

export default TrackingTool1;
