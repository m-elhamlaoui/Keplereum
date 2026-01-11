package com.example.jeeHamlaoui.Sattelites_Service.service;

import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SatelliteModelService {

    private final SatelliteModelRepository satelliteModelRepository;

    @Autowired
    public SatelliteModelService(SatelliteModelRepository satelliteModelRepository) {
        this.satelliteModelRepository = satelliteModelRepository;
    }

    public SatelliteModel saveSatelliteModel(SatelliteModel satelliteModel) {
        return satelliteModelRepository.save(satelliteModel);
    }

    public List<SatelliteModel> getAllSatellitesModel() {
        return satelliteModelRepository.findAll();
    }

    public Optional<SatelliteModel> getSatelliteModelById(Long id) {
        return satelliteModelRepository.findById(id);
    }

    public SatelliteModel updateSatelliteModel(Long id, SatelliteModel updatedSatellite) {
        return satelliteModelRepository.findById(id)
                .map(existingSatellite -> {
                    existingSatellite.setName(updatedSatellite.getName());
                    existingSatellite.setManufacturer(updatedSatellite.getManufacturer());
                    existingSatellite.setWeight(updatedSatellite.getWeight());
                    existingSatellite.setDimensions(updatedSatellite.getDimensions());
                    existingSatellite.setPowerCapacity(updatedSatellite.getPowerCapacity());
                    existingSatellite.setExpectedLifespan(updatedSatellite.getExpectedLifespan());
                    existingSatellite.setDesignTrajectoryPredictionFactor(updatedSatellite.getDesignTrajectoryPredictionFactor());
                    existingSatellite.setLaunchMass(updatedSatellite.getLaunchMass());
                    existingSatellite.setDryMass(updatedSatellite.getDryMass());
                    return satelliteModelRepository.save(existingSatellite);
                })
                .orElseThrow(() -> new RuntimeException("Satellite not found with id " + id));
    }

    public void deleteSatelliteModel(Long id) {
        satelliteModelRepository.deleteById(id);
    }
}
