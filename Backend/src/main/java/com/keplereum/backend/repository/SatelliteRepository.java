package com.keplereum.backend.repository;

import com.keplereum.backend.domain.Satellite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Satellite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SatelliteRepository extends JpaRepository<Satellite, Long> {}
