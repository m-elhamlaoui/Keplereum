package com.example.jeeHamlaoui.Sattelites_Service.repository;

import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteTrajectory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SatelliteTrajectory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SatelliteTrajectoryRepository extends JpaRepository<SatelliteTrajectory, Long> {
    SatelliteTrajectory findTopBySatelliteOrderByEndTimeDesc(Satellite satellite);


    Optional<SatelliteTrajectory> findTopBySatelliteOrderByStartTimeDesc(Satellite satellite);

    List<SatelliteTrajectory> findAllBySatellite_Id(Long id);
}
