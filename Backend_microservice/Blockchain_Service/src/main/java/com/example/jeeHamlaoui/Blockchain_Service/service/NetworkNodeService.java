package com.example.jeeHamlaoui.Blockchain_Service.service;

import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.NetworkNodeDTO;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class NetworkNodeService {
    private final NetworkNodeRepository networkNodeRepository;

    public NetworkNodeService(NetworkNodeRepository networkNodeRepository) {
        this.networkNodeRepository = networkNodeRepository;
    }
    public NetworkNode createNode(NetworkNodeDTO nodeDTO) {
        NetworkNode node = new NetworkNode();
        node.setPublicKey(nodeDTO.getPublicKey());
        node.setNodeName(nodeDTO.getNodeName());
        node.setBlocksValidated(nodeDTO.getBlocksValidated());
        node.setLastActive(Instant.now());


        return networkNodeRepository.save(node);
    }

    @Transactional
    public NetworkNode saveNode(NetworkNode node) {
        node.setLastActive(Instant.now());
        return networkNodeRepository.save(node);
    }

    public List<NetworkNode> getAuthorityNodes() {
        return networkNodeRepository.findByAuthorityStatus(true);
    }

    public List<NetworkNode> getActiveValidators(int minBlocksValidated) {
        return networkNodeRepository.findByBlocksValidatedGreaterThan(minBlocksValidated);
    }

    public void updateLastActive(String publicKey) {
        networkNodeRepository.findById(publicKey).ifPresent(node -> {
            node.setLastActive(Instant.now());
            networkNodeRepository.save(node);
        });
    }
    public Optional<NetworkNode> getNodeByPublicKey(String publicKey){
        return networkNodeRepository.findById(publicKey);
    }
}