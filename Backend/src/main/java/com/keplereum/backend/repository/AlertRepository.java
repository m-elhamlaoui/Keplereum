package com.keplereum.backend.repository;

import com.keplereum.backend.domain.Alert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Alert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {}
