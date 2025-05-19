package com.example.jeeHamlaoui.service;

import com.example.jeeHamlaoui.client.SatelliteClient;
import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.repository.GroundStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroundStationService {

    private final GroundStationRepository groundStationRepository;
    private SatelliteClient satelliteClient;

    @Autowired
    public GroundStationService(GroundStationRepository groundStationRepository, SatelliteClient satelliteClient) {
        this.groundStationRepository = groundStationRepository;
        this.satelliteClient = satelliteClient;
    }


    public List<GroundStation> findAllGroundStations() {
        return groundStationRepository.findAll();
    }

    public Optional<GroundStation> findGroundStationById(Long id) {
        return groundStationRepository.findById(id);
    }

    public GroundStation saveGroundStation(GroundStation groundStation) {
        return groundStationRepository.save(groundStation);
    }

    public GroundStation updateGroundStation(Long id, GroundStation updatedGroundStation) {
        return groundStationRepository.findById(id)
                .map(existingGroundStation -> {
                    existingGroundStation.setGroundStation_Name(updatedGroundStation.getGroundStation_Name());
                    existingGroundStation.setGroundStation_Email(updatedGroundStation.getGroundStation_Email());
                    existingGroundStation.setGroundStation_AccesLevel(updatedGroundStation.getGroundStation_AccesLevel());
                    existingGroundStation.setGroundStation_Latitude(updatedGroundStation.getGroundStation_Latitude());
                    existingGroundStation.setGroundStation_Longitude(updatedGroundStation.getGroundStation_Longitude());
                    existingGroundStation.setGroundStation_Description(updatedGroundStation.getGroundStation_Description());
                    return groundStationRepository.save(existingGroundStation);
                })
                .orElseThrow(() -> new RuntimeException("GroundStation not found with id: " + id));
    }

    public void deleteGroundStation(Long id) {
        groundStationRepository.deleteById(id);
    }


    public SatelliteByGroundStationResponse findSatelliteByUserId(Long id) {
        var groundStation = groundStationRepository.findByUser_UserId(id);
        if (groundStation.isEmpty()) {
            throw new RuntimeException("GroundStation not found with id: " + id);
        }
        var satellites = satelliteClient.findAllSatellitesByGroundStationId(groundStation.get().getGroundStation_id());
        return new SatelliteByGroundStationResponse(satellites);
    }
}
