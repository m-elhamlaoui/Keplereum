package com.example.jeeHamlaoui.Blockchain_Service.service;


import com.example.jeeHamlaoui.Blockchain_Service.model.*;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.BlockDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.TransactionDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.AlertType;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlockService {
    private final BlockRepository blockRepository;
    private final BlockTransactionRepository blockTransactionRepository;
    private NetworkNodeRepository networkNodeRepository;

    public BlockService(BlockRepository blockRepository, BlockTransactionRepository blockTransactionRepository, NetworkNodeRepository networkNodeRepository) {
        this.blockRepository = blockRepository;
        this.blockTransactionRepository = blockTransactionRepository;
        this.networkNodeRepository = networkNodeRepository;
    }

    @Transactional
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    @Transactional
    public List<Block> saveAllBlocks(List<Block> blocks) {
        return blockRepository.saveAll(blocks);
    }

    public Optional<Block> getBlockByHeight(Long height) {
        return Optional.ofNullable(blockRepository.findByHeight(height));
    }

    public Optional<Block> getBlockByHash(String hash) {
        return Optional.ofNullable(blockRepository.findByCurrentBlockHash(hash));
    }

    public Optional<Block> getLatestBlock() {
        return Optional.ofNullable(blockRepository.findTopByOrderByHeightDesc());
    }

    public boolean blockExists(Long height) {
        return blockRepository.existsByHeight(height);
    }

    public boolean blockExists(String hash) {
        return blockRepository.existsByCurrentBlockHash(hash);
    }
    @Transactional
    public Void createBlock(BlockDTO blockDTO) {
        // Validate block uniqueness first
        if (blockRepository.existsById(blockDTO.getNumber())) {
            throw new IllegalStateException("Block height exists: " + blockDTO.getNumber());
        }
        if (blockRepository.existsByCurrentBlockHash(blockDTO.getHash())) {
            throw new IllegalStateException("Block hash exists: " + blockDTO.getHash());
        }

        // Get validator - this throws if not found
        NetworkNode validator = networkNodeRepository.getReferenceById(blockDTO.getValidator());


        // Create block
        Block block = new Block();
        block.setHeight(blockDTO.getNumber());
        block.setCurrentBlockHash(blockDTO.getHash());
        block.setPreviousBlockHash(blockDTO.getParentHash());
        block.setSha3Uncles(blockDTO.getSha3uncles());
        block.setTransactionRoot(blockDTO.getTransactionRoot());
        block.setCreatedAt(blockDTO.getTimestamp() != null ? blockDTO.getTimestamp() : Instant.now());
        block.setBlockWeight(blockDTO.getSize() != null ? blockDTO.getSize().doubleValue() : 0.0);
        block.setBlockSize(blockDTO.getSize() != null ? blockDTO.getSize().toString() : "0");
        block.setValidator(validator);

        // Process transactions
        if (blockDTO.getTransactions() != null && !blockDTO.getTransactions().isEmpty()) {
            List<BlockTransaction> transactions = new ArrayList<>();

            for (TransactionDTO txDTO : blockDTO.getTransactions()) {
                try {
                    BlockTransaction tx = new BlockTransaction();
                    tx.setHash(txDTO.getHash());
                    tx.setAmount(txDTO.getAmount());
                    tx.setFee(txDTO.getFee());
                    tx.setStatus(txDTO.getStatus());
                    tx.setGasPrice(txDTO.getGasPrice());
                    tx.setGasLimit(txDTO.getGasLimit());
                    tx.setGasUsed(txDTO.getGasUsed());
                    tx.setBlockNumber(block.getHeight());
                    tx.setCreatedAt(txDTO.getTimestamp() != null ? txDTO.getTimestamp() : Instant.now());
                    tx.setBlock(block);

                    // Critical change: Use getReferenceById instead of findByPublicKey
                    tx.setSender(networkNodeRepository.getReferenceById(txDTO.getFrom()));
                    tx.setReceiver(networkNodeRepository.getReferenceById(txDTO.getTo()));

                    // Handle transaction type
                    if (txDTO.getAlertType() != null) {
                        Alert alert = new Alert();
                        alert.setAlertType(AlertType.valueOf(txDTO.getAlertType()));
                        alert.setLatitude(txDTO.getLatitude());
                        alert.setLongitude(txDTO.getLongitude());
                        tx.setTransactionType(alert);
                    } else if (txDTO.getConfirmsAlertId() != null) {
                        tx.setTransactionType(new Confirmation());
                    } else if (txDTO.getAction() != null) {
                        ValidatorAction action = new ValidatorAction();
                        action.setActionType(txDTO.getAction());
                        tx.setTransactionType(action);
                    }

                    transactions.add(tx);
                } catch (EntityNotFoundException e) {
                    // Skip invalid transactions
                    continue;
                }
            }

            block.setTransactions(transactions);
        }

        blockRepository.save(block);
        return null;
    }
}