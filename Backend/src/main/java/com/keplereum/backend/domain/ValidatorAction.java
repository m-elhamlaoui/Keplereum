package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keplereum.backend.domain.enumeration.ActionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ValidatorAction.
 */
@Entity
@Table(name = "validator_action")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "validatoraction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ValidatorAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    private ActionType actionType;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @JsonIgnoreProperties(value = { "alert", "confirmations", "blockTransactions", "validatorAction" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Event event;

    @JsonIgnoreProperties(value = { "confirmations", "validatorAction", "event" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Alert alert;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ValidatorAction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public ValidatorAction actionType(ActionType actionType) {
        this.setActionType(actionType);
        return this;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public ValidatorAction createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ValidatorAction event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Alert getAlert() {
        return this.alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public ValidatorAction alert(Alert alert) {
        this.setAlert(alert);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValidatorAction)) {
            return false;
        }
        return getId() != null && getId().equals(((ValidatorAction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValidatorAction{" +
            "id=" + getId() +
            ", actionType='" + getActionType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
