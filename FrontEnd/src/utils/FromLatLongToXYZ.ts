

import { earthRadiusSceneUnits } from './constants';
import * as THREE from 'three'

export  function FromLatLongToXYZ(lat:number,lon:number,radius:number=4):Array<number>{
  
    const phi   = (90-lat)*(Math.PI/180);
    const theta = (lon+180)*(Math.PI/18)
    const x = -(radius * Math.sin(phi)*Math.cos(theta));
    const z = (radius * Math.sin(phi)*Math.sin(theta));
    const y = (radius * Math.cos(phi));
  
    return [x,y,z];

}

  
  /**
   * Projects a 3D point onto the surface of a sphere centered at the origin.
   * @param point - The 3D point to project.
   * @param radius - The radius of the sphere (e.g., 6371 for Earth in km).
   * @returns The projected point on the sphere surface.
   */
export function projectConeToEarth(point: THREE.Vector3): THREE.Vector3 {
 
    const point2 = new THREE.Vector3();

    return  point2.copy(point).normalize().multiplyScalar(earthRadiusSceneUnits);

  }
  