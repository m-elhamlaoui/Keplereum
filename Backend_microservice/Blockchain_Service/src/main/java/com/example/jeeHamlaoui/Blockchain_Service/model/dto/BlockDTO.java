package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

public class BlockDTO {
    private Long number;
    private String hash;
    private String parentHash;
    private String sha3uncles;
    private String transactionRoot;
    private Instant timestamp;
    @NotBlank
    private String validator;
    private Integer size;
    private Double gasUsed;
    private Double gasLimit;
    private List<TransactionDTO> transactions;
    private Integer transactionCount;
    private Double totalFees;

    // Getters and setters

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Double getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(Double totalFees) {
        this.totalFees = totalFees;
    }

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public Double getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(Double gasLimit) {
        this.gasLimit = gasLimit;
    }

    public Double getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(Double gasUsed) {
        this.gasUsed = gasUsed;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionRoot() {
        return transactionRoot;
    }

    public void setTransactionRoot(String transactionRoot) {
        this.transactionRoot = transactionRoot;
    }

    public String getSha3uncles() {
        return sha3uncles;
    }

    public void setSha3uncles(String sha3uncles) {
        this.sha3uncles = sha3uncles;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
