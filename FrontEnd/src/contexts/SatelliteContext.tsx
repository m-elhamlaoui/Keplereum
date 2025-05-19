
import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';
import { SatelliteContextType, SatelliteData } from '@/types/satellite';
import { initialGroundStations } from '@/data/satelliteData';
import { loadInitialSatellites } from '@/utils/satelliteLoader';

// Create the context with default values
export const SatelliteContext = createContext<SatelliteContextType>({
  satellites: [],
  setSatellites: () => {},
  selectedSatelliteId: '',
  setSelectedSatelliteId: () => {},
  selectedSatellite: null,
  groundStations: [],
  setGroundStations: () => {},
  loading: true
});

// Custom hook for using the satellite context
export const useSatelliteContext = () => useContext(SatelliteContext);

// Provider component
export const SatelliteProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [satellites, setSatellites] = useState<SatelliteData[]>([]);
  const [selectedSatelliteId, setSelectedSatelliteId] = useState<string>("sat-1");
  const [groundStations, setGroundStations] = useState(initialGroundStations);
  const [loading, setLoading] = useState(true);

  const selectedSatellite = satellites.find(sat => sat.id === selectedSatelliteId) || null;

  useEffect(() => {
    const initializeSatellites = async () => {
      const initializedSatellites = await loadInitialSatellites();
      setSatellites(initializedSatellites);
      setLoading(false);
    };

    initializeSatellites();
  }, []);

  return (
    <SatelliteContext.Provider
      value={{
        satellites,
        setSatellites,
        selectedSatelliteId,
        setSelectedSatelliteId,
        selectedSatellite,
        groundStations,
        setGroundStations,
        loading,
      }}
    >
      {children}
    </SatelliteContext.Provider>
  );
};
