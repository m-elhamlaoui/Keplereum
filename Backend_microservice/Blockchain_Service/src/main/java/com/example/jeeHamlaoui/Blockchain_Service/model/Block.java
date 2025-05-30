package com.example.jeeHamlaoui.Blockchain_Service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.time.Instant;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table(name = "blocks")
public class Block {
    @Id
    @Column(name = "height", nullable = false, unique = true)
    private Long height;

    @Column(name = "previous_block_hash")
    private String previousBlockHash;

    @Column(name = "current_block_hash", nullable = false, unique = true)
    private String currentBlockHash;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "block_weight", nullable = false)
    private Double blockWeight;

    @Column(name = "transaction_root", nullable = false)
    private String transactionRoot;

    @Column(name = "sha3_uncles", nullable = false)
    private String sha3Uncles;

    @Column(name = "block_size", nullable = false)
    private String blockSize;

    // One Block has many Transactions (1:N)
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    @JsonManagedReference("block-transactions")
    private List<BlockTransaction> transactions = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validator_public_key", referencedColumnName = "public_key")
    private NetworkNode validator;

    public NetworkNode getValidator() {
        return validator;
    }

    public void setValidator(NetworkNode validator) {
        this.validator = validator;
    }

    public Block() {
    }

    public Block(Long height,NetworkNode validator, List<BlockTransaction> transactions, String blockSize, String sha3Uncles, String transactionRoot, Double blockWeight, Instant createdAt, String currentBlockHash, String previousBlockHash) {
        this.height = height;
        this.transactions = transactions;
        this.blockSize = blockSize;
        this.sha3Uncles = sha3Uncles;
        this.transactionRoot = transactionRoot;
        this.blockWeight = blockWeight;
        this.createdAt = createdAt;
        this.currentBlockHash = currentBlockHash;
        this.previousBlockHash = previousBlockHash;
        this.validator = validator;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public List<BlockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BlockTransaction> transactions) {
        this.transactions = transactions;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(String blockSize) {
        this.blockSize = blockSize;
    }

    public String getSha3Uncles() {
        return sha3Uncles;
    }

    public void setSha3Uncles(String sha3Uncles) {
        this.sha3Uncles = sha3Uncles;
    }

    public String getTransactionRoot() {
        return transactionRoot;
    }

    public void setTransactionRoot(String transactionRoot) {
        this.transactionRoot = transactionRoot;
    }

    public Double getBlockWeight() {
        return blockWeight;
    }

    public void setBlockWeight(Double blockWeight) {
        this.blockWeight = blockWeight;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrentBlockHash() {
        return currentBlockHash;
    }

    public void setCurrentBlockHash(String currentBlockHash) {
        this.currentBlockHash = currentBlockHash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

// Getters and setters

}