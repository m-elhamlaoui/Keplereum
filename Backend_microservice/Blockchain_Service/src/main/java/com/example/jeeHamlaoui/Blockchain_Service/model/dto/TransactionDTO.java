package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.ActionType;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;

import java.time.Instant;

public class TransactionDTO {
    private String id;
    private String hash;
    private String from;
    private String to;
    private Double amount;
    private Double fee;
    private TransactionStatus status;
    private Instant timestamp;
    private Double gasPrice;
    private Double gasLimit;
    private Double gasUsed;
    private Long blockNumber;
    private String alertType;
    private Double latitude;
    private Double longitude;
    private String confirmsAlertId;
    private ActionType action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getConfirmsAlertId() {
        return confirmsAlertId;
    }

    public void setConfirmsAlertId(String confirmsAlertId) {
        this.confirmsAlertId = confirmsAlertId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Double getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(Double gasUsed) {
        this.gasUsed = gasUsed;
    }

    public Double getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(Double gasPrice) {
        this.gasPrice = gasPrice;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Double getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(Double gasLimit) {
        this.gasLimit = gasLimit;
    }
    // Getters and setters
}