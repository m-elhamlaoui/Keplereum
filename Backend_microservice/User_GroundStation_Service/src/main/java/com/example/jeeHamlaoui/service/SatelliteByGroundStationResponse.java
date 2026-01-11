package com.example.jeeHamlaoui.service;

import com.example.jeeHamlaoui.client.SatelliteDTO;

import java.util.List;

public class SatelliteByGroundStationResponse {

    List<SatelliteDTO> satelliteDTOS;


    public SatelliteByGroundStationResponse() {
    }

    public SatelliteByGroundStationResponse(List<SatelliteDTO> satelliteDTOS) {
        this.satelliteDTOS = satelliteDTOS;
    }

    public List<SatelliteDTO> getSatellites() {
        return satelliteDTOS;
    }

    public void setSatellites(List<SatelliteDTO> satelliteDTOS) {
        this.satelliteDTOS = satelliteDTOS;
    }
}
