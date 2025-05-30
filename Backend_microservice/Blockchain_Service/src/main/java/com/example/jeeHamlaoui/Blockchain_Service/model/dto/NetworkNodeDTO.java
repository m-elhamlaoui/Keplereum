package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class NetworkNodeDTO {
    @NotBlank(message = "Public key is required")
    private String publicKey;

    private String privateKey;

    private String nodeName;

    private boolean authorityStatus;

    private Integer blocksValidated;

    private Instant lastActive;

    public Integer getBlocksValidated() {
        return blocksValidated;
    }

    public void setBlocksValidated(Integer blocksValidated) {
        this.blocksValidated = blocksValidated;
    }

    public Instant getLastActive() {
        return lastActive;
    }

    public void setLastActive(Instant lastActive) {
        this.lastActive = lastActive;
    }

    public @NotBlank String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(@NotBlank String publicKey) {
        this.publicKey = publicKey;
    }

    public boolean isAuthorityStatus() {
        return authorityStatus;
    }

    public void setAuthorityStatus(boolean authorityStatus) {
        this.authorityStatus = authorityStatus;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

// Getters and setters
}