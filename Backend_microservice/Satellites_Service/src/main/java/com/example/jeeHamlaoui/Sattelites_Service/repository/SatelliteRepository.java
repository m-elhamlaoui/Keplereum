package com.example.jeeHamlaoui.Sattelites_Service.repository;

import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Satellite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
    Optional<Satellite> findByName(String name);

    List<Satellite> findAllByGroundStationId(Long GroundStation);
}
