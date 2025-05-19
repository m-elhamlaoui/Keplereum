package com.keplereum.backend.repository;

import com.keplereum.backend.domain.NetworkNode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface NetworkNodeRepositoryWithBagRelationships {
    Optional<NetworkNode> fetchBagRelationships(Optional<NetworkNode> networkNode);

    List<NetworkNode> fetchBagRelationships(List<NetworkNode> networkNodes);

    Page<NetworkNode> fetchBagRelationships(Page<NetworkNode> networkNodes);
}
