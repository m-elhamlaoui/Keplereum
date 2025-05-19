package com.keplereum.backend.repository;

import com.keplereum.backend.domain.Sensor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sensor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {}
