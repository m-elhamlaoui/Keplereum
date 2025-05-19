package com.example.jeeHamlaoui.controller;

import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.service.GroundStationService;
import com.example.jeeHamlaoui.service.SatelliteByGroundStationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groundstations")
@CrossOrigin(origins = "*")
public class GroundStationController {

    private final GroundStationService groundStationService;

    @Autowired
    public GroundStationController(GroundStationService groundStationService) {
        this.groundStationService = groundStationService;
    }

    @GetMapping
    public ResponseEntity<List<GroundStation>> getAllGroundStations() {
        List<GroundStation> groundStations = groundStationService.findAllGroundStations();
        return ResponseEntity.ok(groundStations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroundStation> getGroundStationById(@PathVariable Long id) {
        return groundStationService.findGroundStationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GroundStation> createGroundStation(@RequestBody GroundStation groundStation) {
        GroundStation savedGroundStation = groundStationService.saveGroundStation(groundStation);
        return ResponseEntity.ok(savedGroundStation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroundStation> updateGroundStation(@PathVariable Long id, @RequestBody GroundStation groundStation) {
        try {
            GroundStation updatedGroundStation = groundStationService.updateGroundStation(id, groundStation);
            return ResponseEntity.ok(updatedGroundStation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroundStation(@PathVariable Long id) {
        groundStationService.deleteGroundStation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/satellites/{user-id}")
    public ResponseEntity<SatelliteByGroundStationResponse> findAllSatellites(
            @PathVariable("user-id") Long userId
    ){
        return ResponseEntity.ok(groundStationService.findSatelliteByUserId(userId));
    }
}
