package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Block.
 */
@Entity
@Table(name = "block")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "block")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "height", nullable = false, unique = true)
    private Long height;

    @NotNull
    @Column(name = "previous_block_hash", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String previousBlockHash;

    @NotNull
    @Column(name = "current_block_hash", nullable = false, unique = true)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String currentBlockHash;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "block")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "block", "event", "networkNodes" }, allowSetters = true)
    private Set<BlockTransaction> transactions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "satellite", "blocks", "blockTransactions" }, allowSetters = true)
    private NetworkNode networkNode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Block id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeight() {
        return this.height;
    }

    public Block height(Long height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getPreviousBlockHash() {
        return this.previousBlockHash;
    }

    public Block previousBlockHash(String previousBlockHash) {
        this.setPreviousBlockHash(previousBlockHash);
        return this;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public String getCurrentBlockHash() {
        return this.currentBlockHash;
    }

    public Block currentBlockHash(String currentBlockHash) {
        this.setCurrentBlockHash(currentBlockHash);
        return this;
    }

    public void setCurrentBlockHash(String currentBlockHash) {
        this.currentBlockHash = currentBlockHash;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Block createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<BlockTransaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<BlockTransaction> blockTransactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setBlock(null));
        }
        if (blockTransactions != null) {
            blockTransactions.forEach(i -> i.setBlock(this));
        }
        this.transactions = blockTransactions;
    }

    public Block transactions(Set<BlockTransaction> blockTransactions) {
        this.setTransactions(blockTransactions);
        return this;
    }

    public Block addTransaction(BlockTransaction blockTransaction) {
        this.transactions.add(blockTransaction);
        blockTransaction.setBlock(this);
        return this;
    }

    public Block removeTransaction(BlockTransaction blockTransaction) {
        this.transactions.remove(blockTransaction);
        blockTransaction.setBlock(null);
        return this;
    }

    public NetworkNode getNetworkNode() {
        return this.networkNode;
    }

    public void setNetworkNode(NetworkNode networkNode) {
        this.networkNode = networkNode;
    }

    public Block networkNode(NetworkNode networkNode) {
        this.setNetworkNode(networkNode);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Block)) {
            return false;
        }
        return getId() != null && getId().equals(((Block) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Block{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", previousBlockHash='" + getPreviousBlockHash() + "'" +
            ", currentBlockHash='" + getCurrentBlockHash() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
