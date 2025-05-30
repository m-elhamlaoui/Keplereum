
import { useState, useEffect } from "react";
import SatelliteWindow from "./SatelliteWindow";
import { useSatelliteContext } from "@/contexts/SatelliteContext";
import {
  getCurrentSatelliteTelemetry
} from "@/utils/satelliteUtils";

const InfoComponent = () => {
  const { selectedSatellite, setSatellites } = useSatelliteContext();
  const [telemetryData, setTelemetryData] = useState<{ property: string; value: string | number }[]>([]);
  
  useEffect(() => {
    let intervalId: NodeJS.Timeout | null = null;

    if (selectedSatellite) {
      intervalId = setInterval(() => {
        const updatedTelemetry = getCurrentSatelliteTelemetry(selectedSatellite);

        setSatellites((prevSatellites) =>
          prevSatellites.map((satellite) =>
            satellite.id === selectedSatellite.id
              ? { ...satellite, telemetry: updatedTelemetry }
              : satellite
          )
        );

        setTelemetryData(updatedTelemetry);
      }, 200);
    }

    return () => {
      if (intervalId) clearInterval(intervalId);
    };
  }, [selectedSatellite, setSatellites]);

  return (
    <SatelliteWindow title="Satellite Info" className="col-span-2 row-span-1">
      <div className="p-2 h-full overflow-hidden">
        {selectedSatellite ? (
          <div className="space-y-2">
            <div className="p-1 pl-3 bg-satellite-dark-accent rounded">
              <h3 className="text-xs font-medium text-satellite-highlight">{selectedSatellite.name} Telemetry</h3>
            </div>
            <div className="text-xs space-y-1">
              {telemetryData.map((item, index) => (
                <div 
                  key={index} 
                  className={`flex justify-between p-1 ${
                    index % 2 === 0 ? 'bg-satellite-dark-accent/30' : ''
                  }`}
                >
                  <span className="text-gray-400">{item.property}</span>
                  <span className="text-satellite-text font-mono">{item.value}</span>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <div className="h-full flex items-center justify-center text-gray-500 text-sm">
            No satellite selected
          </div>
        )}
      </div>
    </SatelliteWindow>
  );
};

export default InfoComponent;
