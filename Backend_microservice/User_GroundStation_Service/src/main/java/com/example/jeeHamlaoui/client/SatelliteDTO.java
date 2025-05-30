package com.example.jeeHamlaoui.client;

import com.example.jeeHamlaoui.model.SatelliteStatus;

import java.time.Instant;
import java.util.Set;

public record SatelliteDTO(
        Long satellite_id,
        String name,
        Instant launchDate,
        SatelliteStatus status,
        Long groundStationId,
        String networkNodeId,
        SatelliteModelResponse model,
        Set<SensorResponse> sensors,
        Set<TrajectoryResponse> trajectories
) {

}