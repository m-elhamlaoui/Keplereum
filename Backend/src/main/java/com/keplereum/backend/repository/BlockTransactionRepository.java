package com.keplereum.backend.repository;

import com.keplereum.backend.domain.BlockTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BlockTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockTransactionRepository extends JpaRepository<BlockTransaction, Long> {}
