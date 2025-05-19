package com.example.jeeHamlaoui.Blockchain_Service.controller;

import com.example.jeeHamlaoui.Blockchain_Service.model.BlockTransaction;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.TransactionDTO;
import com.example.jeeHamlaoui.Blockchain_Service.service.BlockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*")
public class BlockTransactionController {
    private final BlockTransactionService transactionService;

    @Autowired
    public BlockTransactionController(BlockTransactionService transactionService) {
        this.transactionService = transactionService;
    }
    /*
    @PostMapping
    public ResponseEntity<BlockTransaction> createTransaction(@RequestBody TransactionDTO transaction) {
        BlockTransaction savedTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }
    */

    @PostMapping("/batch")
    public ResponseEntity<List<BlockTransaction>> createTransactions(@RequestBody List<BlockTransaction> transactions) {
        List<BlockTransaction> savedTransactions = transactionService.saveAllTransactions(transactions);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransactions);
    }

    @GetMapping("/block/{blockNumber}")
    public ResponseEntity<List<BlockTransaction>> getTransactionsByBlock(@PathVariable Long blockNumber) {
        List<BlockTransaction> transactions = transactionService.getTransactionsByBlock(blockNumber);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/sender/{publicKey}")
    public ResponseEntity<List<BlockTransaction>> getTransactionsBySender(@PathVariable String publicKey) {
        List<BlockTransaction> transactions = transactionService.getTransactionsBySender(publicKey);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/receiver/{publicKey}")
    public ResponseEntity<List<BlockTransaction>> getTransactionsByReceiver(@PathVariable String publicKey) {
        List<BlockTransaction> transactions = transactionService.getTransactionsByReceiver(publicKey);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<BlockTransaction> getTransaction(@PathVariable String hash) {
        return transactionService.getTransactionByHash(hash)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}