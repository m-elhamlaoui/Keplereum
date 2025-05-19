package com.example.jeeHamlaoui.Blockchain_Service.repository;

import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findByHeight(Long height);
    Block findByCurrentBlockHash(String hash);
    Block findTopByOrderByHeightDesc();

    boolean existsByCurrentBlockHash(String hash);
    boolean existsByHeight(Long height);
}
