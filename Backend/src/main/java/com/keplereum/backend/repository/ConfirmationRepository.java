package com.keplereum.backend.repository;

import com.keplereum.backend.domain.Confirmation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Confirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {}
