package com.example.jeeHamlaoui.Sattelites_Service.service;

import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SatelliteService {

    private final SatelliteRepository satelliteRepository;

    @Autowired
    public SatelliteService(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    // Create or Update Satellite
    public Satellite save(Satellite satellite) {
        return satelliteRepository.save(satellite);
    }

    // Find by ID
    public Optional<Satellite> findById(Long id) {
        return satelliteRepository.findById(id);
    }

    // Find all Satellites with pagination
    public List<Satellite> findAll() {

        return satelliteRepository.findAll();
    }

    // Delete Satellite by ID
    public void deleteById(Long id) {
        satelliteRepository.deleteById(id);
    }

    // Check if Satellite exists by ID
    public boolean existsById(Long id) {
        return satelliteRepository.existsById(id);
    }

    // Find by name (example of a custom query)
    public Optional<Satellite> findByName(String name) {
        return satelliteRepository.findByName(name);
    }

    public List<Satellite> findAllByGroundStationId(Long userId) {
        return satelliteRepository.findAllByGroundStationId(userId);
    }
}
