package com.keplereum.backend.repository;

import com.keplereum.backend.domain.SatelliteTrajectory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SatelliteTrajectory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SatelliteTrajectoryRepository extends JpaRepository<SatelliteTrajectory, Long> {}
