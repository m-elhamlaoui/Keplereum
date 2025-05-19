package com.example.jeeHamlaoui.Sattelites_Service.repository;

import com.example.jeeHamlaoui.Sattelites_Service.model.Sensor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Sensor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findBySatelliteId(Long id);
}
