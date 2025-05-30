
import { useEffect, useRef } from "react";
import SatelliteWindow from "./SatelliteWindow";
import { useSatelliteContext } from "@/contexts/SatelliteContext";

const Earth2DWindow = () => {
  const { selectedSatellite } = useSatelliteContext();
  const containerRef = useRef<HTMLDivElement>(null);
  
  //** Resize handling **//
  useEffect(() => {
    const handleResize = () => {
      if (containerRef.current) {
      }
    };
    
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  
  return (
    <SatelliteWindow title="2D Earth Window" className="col-span-4 row-span-1">
      <div ref={containerRef} className="h-full w-full bg-[#121619] flex items-center justify-center text-gray-400 flex-col relative">
        <p className="text-base sm:text-lg md:text-xl">2D Earth Rendering Component Placeholder</p>
        {selectedSatellite && (
          <div className="absolute bottom-2 left-2 text-xs sm:text-sm text-gray-400 bg-black/50 px-2 py-1 rounded">
            Tracking: {selectedSatellite.name}
          </div>
        )}
      </div>
    </SatelliteWindow>
  );
};

export default Earth2DWindow;
