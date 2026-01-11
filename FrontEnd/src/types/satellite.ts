
import * as THREE from 'three';

//** The telemetry interface **//
export interface SatelliteTelemetryData {
  property: string;
  value: string | number;
}

//** Orbital data interface **//
export interface OrbitalData {
  cartesianValues: number[];
  points3D: THREE.Vector3[];
  periodSeconds: number;
}

//** Sensor data interface **//
export interface SensorData {
  active: boolean;
  coverageAreaKm: number;
  baseCenterCoordinates: {
    lat: number;
    long: number;
  };
  baseRadiusKm: number;
}

//** Satellite data interface **//
export interface SatelliteData {
  id: string;
  name: string;
  color: string;
  perigeeAltitude: number;
  eccentricity: number;
  inclination: number;
  longitudeOfAscendingNode: number;
  argumentOfPeriapsis: number;
  speedMultiplier: number;
  maxSensorBaseRadius: number;
  maxSensorHeight: number;
  tleEpoch: string;
  orbitalData?: OrbitalData;
  telemetry?: SatelliteTelemetryData[];
  passPredictions?: {
    id: number;
    rise: { date: string; time: string; az: string; deg: string };
    set: { date: string; time: string; az: string; deg: string };
    duration: string;
    visibility: string;
  }[];
  trackData?: {
    color: string;
    rotation: number;
    scale: { x: number; y: number };
  };
  sensorData?: SensorData;
}

//** Ground Station interface **//
export interface GroundStation {
  id: number;
  name: string;
  selected: boolean;
}

//** Context type interface **//
export interface SatelliteContextType {
  satellites: SatelliteData[];
  setSatellites: React.Dispatch<React.SetStateAction<SatelliteData[]>>;
  selectedSatelliteId: string;
  setSelectedSatelliteId: (id: string) => void;
  selectedSatellite: SatelliteData | null;
  groundStations: GroundStation[];
  setGroundStations: React.Dispatch<React.SetStateAction<GroundStation[]>>;
  loading: boolean;
}
