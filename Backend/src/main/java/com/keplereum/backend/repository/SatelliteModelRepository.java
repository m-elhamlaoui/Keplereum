package com.keplereum.backend.repository;

import com.keplereum.backend.domain.SatelliteModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SatelliteModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SatelliteModelRepository extends JpaRepository<SatelliteModel, Long> {}
