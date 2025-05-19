package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A NetworkNode.
 */
@Entity
@Table(name = "network_node")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "networknode")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NetworkNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "public_key", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String publicKey;

    @NotNull
    @Column(name = "authority_status", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Boolean)
    private Boolean authorityStatus;

    @JsonIgnoreProperties(value = { "sensors", "trajectories", "model", "networkNode", "groundStation" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Satellite satellite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "networkNode")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "transactions", "networkNode" }, allowSetters = true)
    private Set<Block> blocks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_network_node__block_transaction",
        joinColumns = @JoinColumn(name = "network_node_id"),
        inverseJoinColumns = @JoinColumn(name = "block_transaction_id")
    )
    @JsonIgnoreProperties(value = { "block", "event", "networkNodes" }, allowSetters = true)
    private Set<BlockTransaction> blockTransactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NetworkNode id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public NetworkNode publicKey(String publicKey) {
        this.setPublicKey(publicKey);
        return this;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Boolean getAuthorityStatus() {
        return this.authorityStatus;
    }

    public NetworkNode authorityStatus(Boolean authorityStatus) {
        this.setAuthorityStatus(authorityStatus);
        return this;
    }

    public void setAuthorityStatus(Boolean authorityStatus) {
        this.authorityStatus = authorityStatus;
    }

    public Satellite getSatellite() {
        return this.satellite;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }

    public NetworkNode satellite(Satellite satellite) {
        this.setSatellite(satellite);
        return this;
    }

    public Set<Block> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        if (this.blocks != null) {
            this.blocks.forEach(i -> i.setNetworkNode(null));
        }
        if (blocks != null) {
            blocks.forEach(i -> i.setNetworkNode(this));
        }
        this.blocks = blocks;
    }

    public NetworkNode blocks(Set<Block> blocks) {
        this.setBlocks(blocks);
        return this;
    }

    public NetworkNode addBlock(Block block) {
        this.blocks.add(block);
        block.setNetworkNode(this);
        return this;
    }

    public NetworkNode removeBlock(Block block) {
        this.blocks.remove(block);
        block.setNetworkNode(null);
        return this;
    }

    public Set<BlockTransaction> getBlockTransactions() {
        return this.blockTransactions;
    }

    public void setBlockTransactions(Set<BlockTransaction> blockTransactions) {
        this.blockTransactions = blockTransactions;
    }

    public NetworkNode blockTransactions(Set<BlockTransaction> blockTransactions) {
        this.setBlockTransactions(blockTransactions);
        return this;
    }

    public NetworkNode addBlockTransaction(BlockTransaction blockTransaction) {
        this.blockTransactions.add(blockTransaction);
        return this;
    }

    public NetworkNode removeBlockTransaction(BlockTransaction blockTransaction) {
        this.blockTransactions.remove(blockTransaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetworkNode)) {
            return false;
        }
        return getId() != null && getId().equals(((NetworkNode) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NetworkNode{" +
            "id=" + getId() +
            ", publicKey='" + getPublicKey() + "'" +
            ", authorityStatus='" + getAuthorityStatus() + "'" +
            "}";
    }
}
