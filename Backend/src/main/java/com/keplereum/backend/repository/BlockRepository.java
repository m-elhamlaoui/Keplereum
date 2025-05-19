package com.keplereum.backend.repository;

import com.keplereum.backend.domain.Block;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Block entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {}
