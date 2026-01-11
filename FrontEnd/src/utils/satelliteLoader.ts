import { SatelliteData } from '@/types/satellite';
import { 
  calculateSatelliteOrbitalTrajectory,
  getCurrentSatelliteTelemetry
} from '@/utils/satelliteUtils';
import { earthRadius, scaleFactor } from "@/utils/constants";
import { initialSatellitesData } from '@/data/satelliteData';

/**
 * Loads the initial satellite data and calculates their orbital trajectories
 * and telemetry information.
 *
 * This function processes the `initialSatellitesData` by calculating the
 * orbital trajectory for each satellite using its orbital parameters such as
 * perigee altitude, eccentricity, inclination, longitude of ascending node,
 * and argument of periapsis. It also computes the telemetry data for each
 * satellite based on the calculated orbital data.
 *
 * @returns {Promise<SatelliteData[]>} A promise that resolves to an array of
 * satellite data, including their orbital trajectories and telemetry information.
 */
export const loadInitialSatellites = async (): Promise<SatelliteData[]> => {
  const satellites = initialSatellitesData.map(satellite => {
    const result = calculateSatelliteOrbitalTrajectory(
      satellite.perigeeAltitude,
      satellite.eccentricity,
      satellite.inclination,
      satellite.longitudeOfAscendingNode,
      satellite.argumentOfPeriapsis,
      earthRadius,
      scaleFactor
    );

    const telemetry = getCurrentSatelliteTelemetry({
      ...satellite,
      orbitalData: {
        cartesianValues: result.cartesianValues,
        points3D: result.points3D,
        periodSeconds: result.periodSeconds,
      },
    });

    return {
      ...satellite,
      orbitalData: {
        cartesianValues: result.cartesianValues,
        points3D: result.points3D,
        periodSeconds: result.periodSeconds,
      },
      telemetry,
    };
  });

  return satellites;
};
