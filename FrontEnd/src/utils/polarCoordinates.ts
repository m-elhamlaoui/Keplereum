
/**
 * Converts Cartesian coordinates to polar coordinates
 * @param x - X coordinate in Cartesian system
 * @param y - Y coordinate in Cartesian system
 * @param z - Z coordinate in Cartesian system
 * @returns Object containing radius, azimuth (in degrees), and elevation (in degrees)
 */
export function cartesianToPolar(x: number, y: number, z: number): {
  radius: number;
  azimuth: number;
  elevation: number;
} {
  const radius = Math.sqrt(x * x + y * y + z * z);
  
  // Calculate azimuth angle (horizontal angle, 0° is North, 90° is East)
  // Convert from [-π, π] to [0, 2π] then to degrees
  const azimuth = ((Math.atan2(y, x) + 2 * Math.PI) % (2 * Math.PI)) * (180 / Math.PI);
  
  // Calculate elevation angle (vertical angle from horizon)
  // Convert from [-π/2, π/2] to [0, 90] in degrees
  const elevation = 90 - Math.acos(z / radius) * (180 / Math.PI);
  
  return { radius, azimuth, elevation };
}

/**
 * Converts polar coordinates to Cartesian coordinates
 * @param radius - Distance from origin
 * @param azimuth - Horizontal angle in degrees (0° is North, 90° is East)
 * @param elevation - Vertical angle from horizon in degrees
 * @returns Object containing x, y, and z coordinates
 */
export function polarToCartesian(
  radius: number,
  azimuth: number,
  elevation: number
): { x: number; y: number; z: number } {
  // Convert degrees to radians
  const azimuthRad = azimuth * (Math.PI / 180);
  const elevationRad = (90 - elevation) * (Math.PI / 180);
  
  const x = radius * Math.cos(azimuthRad) * Math.sin(elevationRad);
  const y = radius * Math.sin(azimuthRad) * Math.sin(elevationRad);
  const z = radius * Math.cos(elevationRad);
  
  return { x, y, z };
}

/**
 * Calculates track plotting points for a polar plot
 * @param radius - Distance from origin
 * @param azimuth - Azimuth angle in degrees
 * @param elevation - Elevation angle in degrees
 * @returns Object with x and y coordinates for a 2D plot representation
 */
export function calculatePolarPlotPoint(
  radius: number,
  azimuth: number,
  elevation: number
): { x: number; y: number } {
  // In a polar plot, the radial distance depends on elevation (90° elevation is center)
  // We map the elevation from 0-90° to radius-0 
  // (outer edge of circle is horizon, center is zenith)
  const plotRadius = Math.max(0, Math.min(1, (90 - elevation) / 90));
  
  // Azimuth determines the angle on the 2D plot
  // We subtract 90 to align north with the top (azimuth 0 = top of circle)
  const azimuthRad = (azimuth - 90) * (Math.PI / 180);
  
  // Calculate x,y position on the unit circle
  // We're calculating from center (0.5, 0.5) with a max radius of 0.5
  const x = 0.5 + plotRadius * 0.5 * Math.cos(azimuthRad);
  const y = 0.5 + plotRadius * 0.5 * Math.sin(azimuthRad);
  
  return { x, y };
}

// Store satellite states for continuous trajectory tracking
interface SatelliteState {
  position: { x: number; y: number; z: number };
  velocity: { x: number; y: number; z: number };
  lastUpdateTime: number;
  trajectoryPoints: { x: number; y: number }[];
  polarCoordinates: { radius: number; azimuth: number; elevation: number }[];
}

const satelliteStates: { [key: string]: SatelliteState } = {};

/**
 * Integrates state propagation with polar coordinate computation
 * @param position - Current position in Cartesian coordinates
 * @param velocity - Current velocity in Cartesian coordinates
 * @param deltaTime - Time step in seconds
 * @param satelliteId - Optional satellite ID for state tracking
 * @returns Object containing new position, velocity, and polar coordinates
 */
export function propagateState(
  position: { x: number; y: number; z: number },
  velocity: { x: number; y: number; z: number },
  deltaTime: number,
  satelliteId?: string
): {
  position: { x: number; y: number; z: number };
  velocity: { x: number; y: number; z: number };
  polar: { radius: number; azimuth: number; elevation: number };
  plotPoint: { x: number; y: number };
  trajectoryPoints?: { x: number; y: number }[];
  polarCoordinates?: { radius: number; azimuth: number; elevation: number }[];
} {
  // Simple propagation model (can be replaced with more accurate orbital mechanics)
  const newPosition = {
    x: position.x + velocity.x * deltaTime,
    y: position.y + velocity.y * deltaTime,
    z: position.z + velocity.z * deltaTime
  };
  
  // Add small perturbations to velocity
  const newVelocity = {
    x: velocity.x + (Math.random() - 0.5) * 0.01,
    y: velocity.y + (Math.random() - 0.5) * 0.01,
    z: velocity.z + (Math.random() - 0.5) * 0.01
  };
  
  // Convert to polar coordinates
  const polar = cartesianToPolar(newPosition.x, newPosition.y, newPosition.z);
  
  // Calculate plot point for polar plot
  const plotPoint = calculatePolarPlotPoint(polar.radius, polar.azimuth, polar.elevation);
  
  // If a satellite ID is provided, store/update trajectory history
  if (satelliteId) {
    if (!satelliteStates[satelliteId]) {
      satelliteStates[satelliteId] = {
        position: newPosition,
        velocity: newVelocity,
        lastUpdateTime: Date.now(),
        trajectoryPoints: [plotPoint],
        polarCoordinates: [polar]
      };
    } else {
      // Only add a point if it's sufficiently different from the last one (avoid oversampling)
      const lastPoint = satelliteStates[satelliteId].trajectoryPoints[
        satelliteStates[satelliteId].trajectoryPoints.length - 1
      ];
      
      const distance = Math.sqrt(
        Math.pow(plotPoint.x - lastPoint.x, 2) + 
        Math.pow(plotPoint.y - lastPoint.y, 2)
      );
      
      if (distance > 0.005) {
        satelliteStates[satelliteId].trajectoryPoints.push(plotPoint);
        satelliteStates[satelliteId].polarCoordinates.push(polar);
        
        // Limit the number of points to avoid memory issues
        if (satelliteStates[satelliteId].trajectoryPoints.length > 500) {
          satelliteStates[satelliteId].trajectoryPoints.shift();
          satelliteStates[satelliteId].polarCoordinates.shift();
        }
      }
      
      satelliteStates[satelliteId].position = newPosition;
      satelliteStates[satelliteId].velocity = newVelocity;
      satelliteStates[satelliteId].lastUpdateTime = Date.now();
    }
    
    // Return trajectory history
    return {
      position: newPosition,
      velocity: newVelocity,
      polar,
      plotPoint,
      trajectoryPoints: [...satelliteStates[satelliteId].trajectoryPoints],
      polarCoordinates: [...satelliteStates[satelliteId].polarCoordinates]
    };
  }
  
  return {
    position: newPosition,
    velocity: newVelocity,
    polar,
    plotPoint
  };
}

/**
 * Gets the current state for a satellite
 * @param satelliteId - Satellite ID
 * @returns Current satellite state or undefined if not available
 */
export function getSatelliteState(satelliteId: string): SatelliteState | undefined {
  return satelliteStates[satelliteId];
}

/**
 * Initializes or resets a satellite's state
 * @param satelliteId - Satellite ID
 * @param position - Initial position
 * @param velocity - Initial velocity 
 */
export function initializeSatelliteState(
  satelliteId: string,
  position: { x: number; y: number; z: number },
  velocity: { x: number; y: number; z: number }
): void {
  const polar = cartesianToPolar(position.x, position.y, position.z);
  const plotPoint = calculatePolarPlotPoint(polar.radius, polar.azimuth, polar.elevation);
  
  satelliteStates[satelliteId] = {
    position,
    velocity,
    lastUpdateTime: Date.now(),
    trajectoryPoints: [plotPoint],
    polarCoordinates: [polar]
  };
}
