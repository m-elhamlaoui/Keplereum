package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "event")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "status", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String status;

    @JsonIgnoreProperties(value = { "confirmations", "validatorAction", "event" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Alert alert;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "event", "alert" }, allowSetters = true)
    private Set<Confirmation> confirmations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "block", "event", "networkNodes" }, allowSetters = true)
    private Set<BlockTransaction> blockTransactions = new HashSet<>();

    @JsonIgnoreProperties(value = { "event", "alert" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event")
    @org.springframework.data.annotation.Transient
    private ValidatorAction validatorAction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Event createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return this.status;
    }

    public Event status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Alert getAlert() {
        return this.alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Event alert(Alert alert) {
        this.setAlert(alert);
        return this;
    }

    public Set<Confirmation> getConfirmations() {
        return this.confirmations;
    }

    public void setConfirmations(Set<Confirmation> confirmations) {
        if (this.confirmations != null) {
            this.confirmations.forEach(i -> i.setEvent(null));
        }
        if (confirmations != null) {
            confirmations.forEach(i -> i.setEvent(this));
        }
        this.confirmations = confirmations;
    }

    public Event confirmations(Set<Confirmation> confirmations) {
        this.setConfirmations(confirmations);
        return this;
    }

    public Event addConfirmation(Confirmation confirmation) {
        this.confirmations.add(confirmation);
        confirmation.setEvent(this);
        return this;
    }

    public Event removeConfirmation(Confirmation confirmation) {
        this.confirmations.remove(confirmation);
        confirmation.setEvent(null);
        return this;
    }

    public Set<BlockTransaction> getBlockTransactions() {
        return this.blockTransactions;
    }

    public void setBlockTransactions(Set<BlockTransaction> blockTransactions) {
        if (this.blockTransactions != null) {
            this.blockTransactions.forEach(i -> i.setEvent(null));
        }
        if (blockTransactions != null) {
            blockTransactions.forEach(i -> i.setEvent(this));
        }
        this.blockTransactions = blockTransactions;
    }

    public Event blockTransactions(Set<BlockTransaction> blockTransactions) {
        this.setBlockTransactions(blockTransactions);
        return this;
    }

    public Event addBlockTransaction(BlockTransaction blockTransaction) {
        this.blockTransactions.add(blockTransaction);
        blockTransaction.setEvent(this);
        return this;
    }

    public Event removeBlockTransaction(BlockTransaction blockTransaction) {
        this.blockTransactions.remove(blockTransaction);
        blockTransaction.setEvent(null);
        return this;
    }

    public ValidatorAction getValidatorAction() {
        return this.validatorAction;
    }

    public void setValidatorAction(ValidatorAction validatorAction) {
        if (this.validatorAction != null) {
            this.validatorAction.setEvent(null);
        }
        if (validatorAction != null) {
            validatorAction.setEvent(this);
        }
        this.validatorAction = validatorAction;
    }

    public Event validatorAction(ValidatorAction validatorAction) {
        this.setValidatorAction(validatorAction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return getId() != null && getId().equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
