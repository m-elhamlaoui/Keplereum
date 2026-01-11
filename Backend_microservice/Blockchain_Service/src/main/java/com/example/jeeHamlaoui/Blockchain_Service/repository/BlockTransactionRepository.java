package com.example.jeeHamlaoui.Blockchain_Service.repository;

import com.example.jeeHamlaoui.Blockchain_Service.model.BlockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockTransactionRepository extends JpaRepository<BlockTransaction, String> {
    List<BlockTransaction> findByBlockNumber(Long blockNumber);
    List<BlockTransaction> findBySenderPublicKey(String publicKey);
    List<BlockTransaction> findByReceiverPublicKey(String publicKey);

}
