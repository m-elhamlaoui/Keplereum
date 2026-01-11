package com.example.jeeHamlaoui.Sattelites_Service.controller;


import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.Sensor;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SensorRequest;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SensorResponse;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteRepository;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteService;
import com.example.jeeHamlaoui.Sattelites_Service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sensors")
@CrossOrigin(origins = "*")
public class SensorController {

    private final SensorService sensorService;


    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;

    }

    @PostMapping
    public ResponseEntity<SensorResponse> createSensor(@RequestBody SensorRequest sensor) {

        return ResponseEntity.ok(sensorService.createSensor(sensor));
    }

    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.findAll();
        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        return sensorService.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(
            @PathVariable Long id,
            @RequestBody Sensor sensor) {
        try {
            Sensor updatedSensor = sensorService.updateSensor(id,sensor);
            return ResponseEntity.ok(updatedSensor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
