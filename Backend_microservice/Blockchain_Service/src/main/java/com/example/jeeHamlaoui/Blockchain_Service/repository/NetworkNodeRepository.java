package com.example.jeeHamlaoui.Blockchain_Service.repository;

import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NetworkNodeRepository extends JpaRepository<NetworkNode, String> {
    List<NetworkNode> findByAuthorityStatus(Boolean authorityStatus);
    List<NetworkNode> findByBlocksValidatedGreaterThan(Integer minBlocks);


    Optional<NetworkNode> findByPublicKey(String validator);
}
