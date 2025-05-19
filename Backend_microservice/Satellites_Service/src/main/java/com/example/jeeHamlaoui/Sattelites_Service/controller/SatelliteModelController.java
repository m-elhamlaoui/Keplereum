package com.example.jeeHamlaoui.Sattelites_Service.controller;


import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/models")
@CrossOrigin(origins = "*")
public class SatelliteModelController {

    private final SatelliteModelService satelliteModelService;

    @Autowired
    public SatelliteModelController(SatelliteModelService satelliteModelService) {
        this.satelliteModelService = satelliteModelService;
    }

    @PostMapping
    public ResponseEntity<SatelliteModel> createModel(@RequestBody SatelliteModel satelliteModel) {
        SatelliteModel savedModel = satelliteModelService.saveSatelliteModel(satelliteModel);
        return ResponseEntity.ok(savedModel);
    }

    @GetMapping
    public ResponseEntity<List<SatelliteModel>> getAllModels() {
        List<SatelliteModel> models = satelliteModelService.getAllSatellitesModel();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SatelliteModel> getModelById(@PathVariable Long id) {
        return satelliteModelService.getSatelliteModelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SatelliteModel> updateModel(
            @PathVariable Long id,
            @RequestBody SatelliteModel satelliteModel) {
        try {
            SatelliteModel updatedModel = satelliteModelService.updateSatelliteModel(id, satelliteModel);
            return ResponseEntity.ok(updatedModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        satelliteModelService.deleteSatelliteModel(id);
        return ResponseEntity.noContent().build();
    }
}
