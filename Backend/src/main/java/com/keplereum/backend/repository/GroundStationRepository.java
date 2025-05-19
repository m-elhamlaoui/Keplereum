package com.keplereum.backend.repository;

import com.keplereum.backend.domain.GroundStation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GroundStation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroundStationRepository extends JpaRepository<GroundStation, Long> {}
