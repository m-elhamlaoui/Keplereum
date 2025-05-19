package com.example.jeeHamlaoui.Blockchain_Service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "network_nodes")
public class NetworkNode {
    @Id
    @Column(name = "public_key", nullable = false, unique = true)
    private String publicKey;

    @Column(name = "private_key", nullable = false)
    private String privateKey;

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }

    private String NodeName;

    @Column(name = "authority_status", nullable = false)
    private boolean authorityStatus;

    @Column(name = "blocks_validated")
    private Integer blocksValidated = 0;

    @Column(name = "last_active", nullable = false)
    private Instant lastActive;

    // Owning side of the Satellite relationship (1:1)
    /*
    @OneToOne(mappedBy = "networkNode")
    private Satellite satellite;
    */
    // Transactions sent by this node (One-to-Many)
    @OneToMany(mappedBy = "sender")
    @JsonIgnore // ← Matches sender reference
    private List<BlockTransaction> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore  // ← Matches receiver reference
    private List<BlockTransaction> receivedTransactions = new ArrayList<>();

    // Getters and setters


    public NetworkNode() {
    }

    public NetworkNode(String publicKey, String privateKey, String nodeName, boolean authorityStatus, Integer blocksValidated, Instant lastActive) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.NodeName = nodeName;
        this.authorityStatus = authorityStatus;
        this.blocksValidated = blocksValidated;
        this.lastActive = lastActive;
    }

    public List<BlockTransaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<BlockTransaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    public List<BlockTransaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<BlockTransaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    /*
    public Satellite getSatellite() {
        return satellite;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }
   */
    public Instant getLastActive() {
        return lastActive;
    }

    public void setLastActive(Instant lastActive) {
        this.lastActive = lastActive;
    }

    public Integer getBlocksValidated() {
        return blocksValidated;
    }

    public void setBlocksValidated(Integer blocksValidated) {
        this.blocksValidated = blocksValidated;
    }

    public boolean isAuthorityStatus() {
        return authorityStatus;
    }

    public void setAuthorityStatus(boolean authorityStatus) {
        this.authorityStatus = authorityStatus;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
