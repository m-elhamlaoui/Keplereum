
import React, { useEffect, useRef } from "react";
import SatelliteWindow from "./SatelliteWindow";
import { useSatelliteContext } from "@/contexts/SatelliteContext";
import SatelliteViewer from "./SatelliteViewer";

const Earth3DWindow = () => {
  const { selectedSatellite } = useSatelliteContext();
  const containerRef = useRef<HTMLDivElement>(null);
  
  //** Resize handling for proper scaling **//
  useEffect(() => {
    const handleResize = () => {
      window.dispatchEvent(new Event('resize'));
    };
    
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  
  return (
    <SatelliteWindow title="3D Earth Window" className="col-span-4 row-span-1">
      <div ref={containerRef} className="h-full w-full bg-[#121619] flex items-center justify-center text-gray-400 flex-col relative">
        <SatelliteViewer />
        {selectedSatellite && (
          <div className="absolute bottom-2 left-2 text-xs text-gray-400 bg-black/50 px-2 py-1 rounded">
            Selected: {selectedSatellite.name}
          </div>
        )}
      </div>
    </SatelliteWindow>
  );
};

export default Earth3DWindow;
