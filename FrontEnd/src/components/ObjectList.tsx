import SatelliteWindow from "./SatelliteWindow";
import { Satellite, MapPin } from "lucide-react";
import { useSatelliteContext } from "@/contexts/SatelliteContext";

const ObjectList = () => {
  const { 
    satellites, 
    selectedSatelliteId, 
    setSelectedSatelliteId,
    groundStations,
    setGroundStations
  } = useSatelliteContext();

  const handleSatelliteSelect = (satId: string) => {
    setSelectedSatelliteId(satId);
  };

  const handleGroundStationSelect = (stationName: string) => {
    setGroundStations(prev => 
      prev.map(station => ({
        ...station,
        selected: station.name === stationName
      }))
    );
  };

  return (
    <SatelliteWindow title="Object List" className="col-span-2 row-span-1">
      <div className="p-2">
        <div className="mb-4">
          <h3 className="flex items-center gap-1 text-sm mb-2 font-medium">
            <Satellite size={16} className="text-satellite-accent" />
            <span>Satellites</span>
          </h3>
          <ul className="space-y-1 pl-6">
            {satellites.map((sat) => (
              <li 
                key={sat.id} 
                onClick={() => handleSatelliteSelect(sat.id)}
                className={`flex items-center gap-1 text-sm cursor-pointer hover:text-satellite-accent ${
                  sat.id === selectedSatelliteId ? "text-satellite-highlight font-medium" : ""
                }`}
              >
                <Satellite 
                  size={12} 
                  className={sat.id === selectedSatelliteId ? "text-satellite-highlight" : ""} 
                  style={{ color: sat.id === selectedSatelliteId ? undefined : sat.color }}
                />
                <span>{sat.name}</span>
              </li>
            ))}
          </ul>
        </div>
        
        <div>
          <h3 className="flex items-center gap-1 text-sm mb-2 font-medium">
            <MapPin size={16} className="text-satellite-accent" />
            <span>Ground Stations</span>
          </h3>
          <ul className="space-y-1 pl-6">
            {groundStations.map((station) => (
              <li 
                key={station.id}
                onClick={() => handleGroundStationSelect(station.name)}
                className={`flex items-center gap-1 text-sm cursor-pointer hover:text-satellite-accent ${
                  station.selected ? "text-satellite-highlight font-medium" : ""
                }`}
              >
                <MapPin size={12} className={station.selected ? "text-satellite-highlight" : ""} />
                <span>{station.name}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </SatelliteWindow>
  );
};

export default ObjectList;
