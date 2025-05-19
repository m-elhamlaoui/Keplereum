package com.example.jeeHamlaoui.Blockchain_Service.service;

import com.example.jeeHamlaoui.Blockchain_Service.exception.BlockNotFoundException;
import com.example.jeeHamlaoui.Blockchain_Service.exception.NodeNotFoundException;
import com.example.jeeHamlaoui.Blockchain_Service.model.*;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.AlertDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.TransactionDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.TransactionTypeDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.ValidatorActionDTO;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import io.micrometer.core.instrument.config.MeterFilter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BlockTransactionService {
    private final BlockTransactionRepository transactionRepository;
    private final NetworkNodeRepository nodeRepository;
    private final BlockRepository blockRepository;

    public BlockTransactionService(BlockTransactionRepository transactionRepository, NetworkNodeRepository nodeRepository, BlockRepository blockRepository) {
        this.transactionRepository = transactionRepository;
        this.nodeRepository = nodeRepository;
        this.blockRepository = blockRepository;
    }

    /*
    public BlockTransaction createTransaction(TransactionDTO dto) {
        // Validate and create base transaction
        BlockTransaction transaction = new BlockTransaction();
        transaction.setHash(dto.getHash());
        transaction.setAmount(dto.getAmount());
        transaction.setFee(dto.getFee());
        transaction.setStatus(dto.getStatus());
        transaction.setGasPrice(dto.getGasPrice());
        transaction.setGasLimit(dto.getGasLimit());
        transaction.setGasUsed(dto.getGasUsed());
        transaction.setCreatedAt(Instant.now());

        // Handle relationships
        if (dto.getBlockHeight() != null) {
            Block block = blockRepository.findById(dto.getBlockHeight())
                    .orElseThrow(() -> new BlockNotFoundException(dto.getBlockHeight()));
            transaction.setBlock(block);
        }

        if (dto.getSenderPublicKey() != null) {
            NetworkNode sender = nodeRepository.findById(dto.getSenderPublicKey())
                    .orElseThrow(() -> new NodeNotFoundException(dto.getSenderPublicKey()));
            transaction.setSender(sender);
        }
        if (dto.getReceiverPublicKey() != null) {
            NetworkNode receiver = nodeRepository.findById(dto.getReceiverPublicKey())
                    .orElseThrow(() -> new NodeNotFoundException(dto.getReceiverPublicKey()));
            transaction.setReceiver(receiver);
        }

        // Handle transaction type polymorphism
        if (dto.getTransactionType() != null) {
            AbstractTransactionType transactionType = createTransactionType(dto.getTransactionType());
            transaction.setTransactionType(transactionType);
        }

        return transactionRepository.save(transaction);
    }

    private AbstractTransactionType createTransactionType(TransactionTypeDTO typeDto) {
        return switch (typeDto.getType()) {
            case ALERT -> {
                AlertDTO alertDto = (AlertDTO) typeDto;
                Alert alert = new Alert();
                alert.setAlertType(alertDto.getAlertType());
                alert.setLatitude(alertDto.getLatitude());
                alert.setLongitude(alertDto.getLongitude());
                yield alert;
            }
            case CONFIRMATION -> new Confirmation();
            case VALIDATOR_ACTION -> {
                ValidatorActionDTO actionDto = (ValidatorActionDTO) typeDto;
                ValidatorAction action = new ValidatorAction();
                action.setActionType(actionDto.getActionType());
                yield action;
            }
            default -> throw new IllegalArgumentException("Unknown transaction type: " + typeDto.getType());
        };
    }
    */



    @Transactional
    public BlockTransaction saveTransaction(BlockTransaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    public List<BlockTransaction> saveAllTransactions(List<BlockTransaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    public List<BlockTransaction> getTransactionsByBlock(Long blockNumber) {
        return transactionRepository.findByBlockNumber(blockNumber);
    }

    public List<BlockTransaction> getTransactionsBySender(String publicKey) {
        return transactionRepository.findBySenderPublicKey(publicKey);
    }

    public List<BlockTransaction> getTransactionsByReceiver(String publicKey) {
        return transactionRepository.findByReceiverPublicKey(publicKey);
    }

    public Optional<BlockTransaction> getTransactionByHash(String hash) {
        return transactionRepository.findById(hash);
    }


}