package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Confirmation.
 */
@Entity
@Table(name = "confirmation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "confirmation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Confirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "alert", "confirmations", "blockTransactions", "validatorAction" }, allowSetters = true)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "confirmations", "validatorAction", "event" }, allowSetters = true)
    private Alert alert;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Confirmation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Confirmation createdAt(Instant createdAt) {
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

    public Confirmation event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Alert getAlert() {
        return this.alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Confirmation alert(Alert alert) {
        this.setAlert(alert);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Confirmation)) {
            return false;
        }
        return getId() != null && getId().equals(((Confirmation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Confirmation{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
