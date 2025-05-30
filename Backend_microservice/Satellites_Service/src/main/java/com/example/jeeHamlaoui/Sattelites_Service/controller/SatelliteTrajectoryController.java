package com.example.jeeHamlaoui.Sattelites_Service.controller;



import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteTrajectory;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SatelliteTrajectoryRequest;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SatelliteTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.model.util.OrbitalTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteTrajectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trajectories")
@CrossOrigin(origins = "*")
public class SatelliteTrajectoryController {

    private final SatelliteTrajectoryService satelliteTrajectoryService;

    @Autowired
    public SatelliteTrajectoryController(SatelliteTrajectoryService satelliteTrajectoryService) {
        this.satelliteTrajectoryService = satelliteTrajectoryService;
    }

    @PostMapping
    public ResponseEntity<SatelliteTrajectoryResponse> createTrajectory(@RequestBody SatelliteTrajectoryRequest satelliteTrajectory) {
        SatelliteTrajectoryResponse savedTrajectory = satelliteTrajectoryService.createTrajectory(satelliteTrajectory);
        return ResponseEntity.ok(savedTrajectory);
    }

    @GetMapping
    public ResponseEntity<List<SatelliteTrajectory>> getAllTrajectoriesById(@RequestParam(name = "satellite_id") Long id) {
        List<SatelliteTrajectory> trajectories = satelliteTrajectoryService.getAllTrajectories(id);
        return ResponseEntity.ok(trajectories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SatelliteTrajectory> getCurrentTrajectoryById(@PathVariable Long id) {
        SatelliteTrajectory trajectory = satelliteTrajectoryService.getCurrentTrajectoryById(id);
                return ResponseEntity.ok(trajectory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SatelliteTrajectory> updateTrajectory(
            @PathVariable Long id,
            @RequestBody SatelliteTrajectory satelliteTrajectory) {
        try {
            SatelliteTrajectory updatedTrajectory = satelliteTrajectoryService.updateTrajectory(id, satelliteTrajectory);
            return ResponseEntity.ok(updatedTrajectory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajectory(@PathVariable Long id) {
        satelliteTrajectoryService.deleteTrajectory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculate-orbit")
    public OrbitalTrajectoryResponse calculateOrbitalTrajectory(
            @RequestParam double perigeeAltitude,
            @RequestParam double eccentricity,
            @RequestParam double inclination,
            @RequestParam double longitudeOfAscendingNode,
            @RequestParam double argumentOfPeriapsis,
            @RequestParam(defaultValue = "6371") double earthRadius ,
            @RequestParam(defaultValue = "6.2784492230419086485638047402292e-4") double scaleFactor
    ) {
        return satelliteTrajectoryService.calculateOrbitalTrajectory(
                perigeeAltitude, eccentricity, inclination,
                longitudeOfAscendingNode, argumentOfPeriapsis,
                earthRadius, (double)scaleFactor);
    }

    @PostMapping("/calculate-orbit/{id}")
    public OrbitalTrajectoryResponse calculateOrbitalTrajectory(
           @PathVariable(name = "id") Long id,
           @RequestParam(defaultValue = "6371") double earthRadius ,
           @RequestParam(defaultValue = "6.2784492230419086485638047402292e-4") double scaleFactor
    ) {
        return satelliteTrajectoryService.calculateOrbitalTrajectoryBySatelliteId(
                    id,earthRadius,scaleFactor
                );
    }
}
