package com.example.jeeHamlaoui.Sattelites_Service.controller;

import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/satellites")
@CrossOrigin(origins = "*")
public class SatelliteController {

    private final SatelliteService satelliteService;

    @Autowired
    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    // Create or Update a Satellite
    @PostMapping
    public ResponseEntity<Satellite> createOrUpdateSatellite(@RequestBody Satellite satellite) {
        Satellite savedSatellite = satelliteService.save(satellite);
        return new ResponseEntity<>(savedSatellite, HttpStatus.CREATED);
    }

    // Get Satellite by ID
    @GetMapping("/{id}")
    public ResponseEntity<Satellite> getSatelliteById(@PathVariable Long id) {
        Optional<Satellite> satellite = satelliteService.findById(id);
        return satellite.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get All Satellites with pagination
    @GetMapping
    public ResponseEntity<?> getAllSatellites() {
        return ResponseEntity.ok(satelliteService.findAll());
    }

    // Delete Satellite by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSatellite(@PathVariable Long id) {
        if (satelliteService.existsById(id)) {
            satelliteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // Get Satellite by Name (example of a custom query)
    @GetMapping("/name/{name}")
    public ResponseEntity<Satellite> getSatelliteByName(@PathVariable String name) {
        Optional<Satellite> satellite = satelliteService.findByName(name);
        return satellite.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/groundStation/{groundStation-id}")
    public ResponseEntity<List<Satellite>> findAllByUserId(@PathVariable("groundStation-id") Long UserId) {
        return ResponseEntity.ok(satelliteService.findAllByGroundStationId(UserId));
    }


}
