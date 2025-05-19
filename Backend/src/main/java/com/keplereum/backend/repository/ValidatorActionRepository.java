package com.keplereum.backend.repository;

import com.keplereum.backend.domain.ValidatorAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ValidatorAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValidatorActionRepository extends JpaRepository<ValidatorAction, Long> {}
