package com.keplereum.backend.repository;

import com.keplereum.backend.domain.NetworkNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class NetworkNodeRepositoryWithBagRelationshipsImpl implements NetworkNodeRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String NETWORKNODES_PARAMETER = "networkNodes";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<NetworkNode> fetchBagRelationships(Optional<NetworkNode> networkNode) {
        return networkNode.map(this::fetchBlockTransactions);
    }

    @Override
    public Page<NetworkNode> fetchBagRelationships(Page<NetworkNode> networkNodes) {
        return new PageImpl<>(
            fetchBagRelationships(networkNodes.getContent()),
            networkNodes.getPageable(),
            networkNodes.getTotalElements()
        );
    }

    @Override
    public List<NetworkNode> fetchBagRelationships(List<NetworkNode> networkNodes) {
        return Optional.of(networkNodes).map(this::fetchBlockTransactions).orElse(Collections.emptyList());
    }

    NetworkNode fetchBlockTransactions(NetworkNode result) {
        return entityManager
            .createQuery(
                "select networkNode from NetworkNode networkNode left join fetch networkNode.blockTransactions where networkNode.id = :id",
                NetworkNode.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<NetworkNode> fetchBlockTransactions(List<NetworkNode> networkNodes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, networkNodes.size()).forEach(index -> order.put(networkNodes.get(index).getId(), index));
        List<NetworkNode> result = entityManager
            .createQuery(
                "select networkNode from NetworkNode networkNode left join fetch networkNode.blockTransactions where networkNode in :networkNodes",
                NetworkNode.class
            )
            .setParameter(NETWORKNODES_PARAMETER, networkNodes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
