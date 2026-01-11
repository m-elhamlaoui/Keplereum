package com.example.jeeHamlaoui.Sattelites_Service.service;

import com.example.jeeHamlaoui.Sattelites_Service.exceptions.ResourceNotFoundException;
import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.Sensor;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SensorRequest;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SensorResponse;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteRepository;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SensorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SatelliteRepository satelliteRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, SatelliteRepository satelliteRepository) {
        this.sensorRepository = sensorRepository;
        this.satelliteRepository = satelliteRepository;
    }

    /**
     * Save a sensor.
     *
     * @param sensor the entity to save.
     * @return the persisted entity.
     */
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    /**
     * Update a sensor.
     *
     * @param sensor the entity to update.
     * @return the updated entity.
     */
    public Sensor update(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    /**
     * Partially update a sensor.
     *
     * @param sensor the entity with fields to update.
     * @return the updated entity.
     */
    public Sensor updateSensor(Long id, Sensor sensorDetails) {
        Sensor existingSensor = sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor not found with id " + id));

        existingSensor.setType(sensorDetails.getType());
        existingSensor.setMaxHeight(sensorDetails.getMaxHeight());
        existingSensor.setMaxRadius(sensorDetails.getMaxRadius());
        existingSensor.setActivity(sensorDetails.getActivity());
        existingSensor.setSatellite(sensorDetails.getSatellite());

        return sensorRepository.save(existingSensor);
    }

    /**
     * Get all the sensors.
     *
     * @return the list of entities.
     */
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    /**
     * Get one sensor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Sensor> findOne(Long id) {
        return sensorRepository.findBySatelliteId(id);
    }

    /**
     * Delete the sensor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        sensorRepository.deleteById(id);
    }

    @Transactional
    public SensorResponse createSensor(SensorRequest request) {
        // 1. Fetch Satellite (throws 404 if not found)
        Satellite satellite = satelliteRepository.findById(request.getSatelliteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Satellite not found with ID: " + request.getSatelliteId()
                ));

        // 2. Create and save Sensor
        Sensor sensor = new Sensor();
        sensor.setType(request.getType());
        sensor.setMaxHeight(request.getMaxHeight());
        sensor.setMaxRadius(request.getMaxRadius());
        sensor.setActivity(request.getActivity());
        sensor.setSatellite(satellite); // Attach managed Satellite

        Sensor savedSensor = sensorRepository.save(sensor);

        return new SensorResponse(savedSensor);
    }
}
