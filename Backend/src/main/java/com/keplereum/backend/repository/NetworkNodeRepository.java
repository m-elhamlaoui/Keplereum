package com.keplereum.backend.repository;

import com.keplereum.backend.domain.NetworkNode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NetworkNode entity.
 *
 * When extending this class, extend NetworkNodeRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface NetworkNodeRepository extends NetworkNodeRepositoryWithBagRelationships, JpaRepository<NetworkNode, Long> {
    default Optional<NetworkNode> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<NetworkNode> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<NetworkNode> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
