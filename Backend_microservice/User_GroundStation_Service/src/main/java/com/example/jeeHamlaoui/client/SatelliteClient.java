package com.example.jeeHamlaoui.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "SATELLITE-SERVICE")
public interface SatelliteClient {

    @GetMapping("/groundStation/{groundStation-id}")
    List<SatelliteDTO> findAllSatellitesByGroundStationId(@PathVariable("groundStation-id") Long GroundStationId);

}
