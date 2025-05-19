package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keplereum.backend.domain.enumeration.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A BlockTransaction.
 */
@Entity
@Table(name = "block_transaction")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "blocktransaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlockTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "hash", nullable = false, unique = true)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String hash;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String from;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String to;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "fee")
    private Double fee;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    private TransactionStatus status;

    @Column(name = "gas_price")
    private Double gasPrice;

    @Column(name = "gas_limit")
    private Double gasLimit;

    @Column(name = "gas_used")
    private Double gasUsed;

    @Column(name = "block_number")
    private Long blockNumber;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "transactions", "networkNode" }, allowSetters = true)
    private Block block;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "alert", "confirmations", "blockTransactions", "validatorAction" }, allowSetters = true)
    private Event event;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "blockTransactions")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "satellite", "blocks", "blockTransactions" }, allowSetters = true)
    private Set<NetworkNode> networkNodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlockTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return this.hash;
    }

    public BlockTransaction hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFrom() {
        return this.from;
    }

    public BlockTransaction from(String from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public BlockTransaction to(String to) {
        this.setTo(to);
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return this.amount;
    }

    public BlockTransaction amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return this.fee;
    }

    public BlockTransaction fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public BlockTransaction status(TransactionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Double getGasPrice() {
        return this.gasPrice;
    }

    public BlockTransaction gasPrice(Double gasPrice) {
        this.setGasPrice(gasPrice);
        return this;
    }

    public void setGasPrice(Double gasPrice) {
        this.gasPrice = gasPrice;
    }

    public Double getGasLimit() {
        return this.gasLimit;
    }

    public BlockTransaction gasLimit(Double gasLimit) {
        this.setGasLimit(gasLimit);
        return this;
    }

    public void setGasLimit(Double gasLimit) {
        this.gasLimit = gasLimit;
    }

    public Double getGasUsed() {
        return this.gasUsed;
    }

    public BlockTransaction gasUsed(Double gasUsed) {
        this.setGasUsed(gasUsed);
        return this;
    }

    public void setGasUsed(Double gasUsed) {
        this.gasUsed = gasUsed;
    }

    public Long getBlockNumber() {
        return this.blockNumber;
    }

    public BlockTransaction blockNumber(Long blockNumber) {
        this.setBlockNumber(blockNumber);
        return this;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public BlockTransaction createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public BlockTransaction block(Block block) {
        this.setBlock(block);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public BlockTransaction event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Set<NetworkNode> getNetworkNodes() {
        return this.networkNodes;
    }

    public void setNetworkNodes(Set<NetworkNode> networkNodes) {
        if (this.networkNodes != null) {
            this.networkNodes.forEach(i -> i.removeBlockTransaction(this));
        }
        if (networkNodes != null) {
            networkNodes.forEach(i -> i.addBlockTransaction(this));
        }
        this.networkNodes = networkNodes;
    }

    public BlockTransaction networkNodes(Set<NetworkNode> networkNodes) {
        this.setNetworkNodes(networkNodes);
        return this;
    }

    public BlockTransaction addNetworkNode(NetworkNode networkNode) {
        this.networkNodes.add(networkNode);
        networkNode.getBlockTransactions().add(this);
        return this;
    }

    public BlockTransaction removeNetworkNode(NetworkNode networkNode) {
        this.networkNodes.remove(networkNode);
        networkNode.getBlockTransactions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockTransaction)) {
            return false;
        }
        return getId() != null && getId().equals(((BlockTransaction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlockTransaction{" +
            "id=" + getId() +
            ", hash='" + getHash() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", amount=" + getAmount() +
            ", fee=" + getFee() +
            ", status='" + getStatus() + "'" +
            ", gasPrice=" + getGasPrice() +
            ", gasLimit=" + getGasLimit() +
            ", gasUsed=" + getGasUsed() +
            ", blockNumber=" + getBlockNumber() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
