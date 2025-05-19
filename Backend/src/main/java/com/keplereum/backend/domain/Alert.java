package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keplereum.backend.domain.enumeration.AlertType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Alert.
 */
@Entity
@Table(name = "alert")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "alert")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    private AlertType alertType;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alert")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "event", "alert" }, allowSetters = true)
    private Set<Confirmation> confirmations = new HashSet<>();

    @JsonIgnoreProperties(value = { "event", "alert" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "alert")
    @org.springframework.data.annotation.Transient
    private ValidatorAction validatorAction;

    @JsonIgnoreProperties(value = { "alert", "confirmations", "blockTransactions", "validatorAction" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "alert")
    @org.springframework.data.annotation.Transient
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlertType getAlertType() {
        return this.alertType;
    }

    public Alert alertType(AlertType alertType) {
        this.setAlertType(alertType);
        return this;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Alert latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Alert longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Alert createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Confirmation> getConfirmations() {
        return this.confirmations;
    }

    public void setConfirmations(Set<Confirmation> confirmations) {
        if (this.confirmations != null) {
            this.confirmations.forEach(i -> i.setAlert(null));
        }
        if (confirmations != null) {
            confirmations.forEach(i -> i.setAlert(this));
        }
        this.confirmations = confirmations;
    }

    public Alert confirmations(Set<Confirmation> confirmations) {
        this.setConfirmations(confirmations);
        return this;
    }

    public Alert addConfirmation(Confirmation confirmation) {
        this.confirmations.add(confirmation);
        confirmation.setAlert(this);
        return this;
    }

    public Alert removeConfirmation(Confirmation confirmation) {
        this.confirmations.remove(confirmation);
        confirmation.setAlert(null);
        return this;
    }

    public ValidatorAction getValidatorAction() {
        return this.validatorAction;
    }

    public void setValidatorAction(ValidatorAction validatorAction) {
        if (this.validatorAction != null) {
            this.validatorAction.setAlert(null);
        }
        if (validatorAction != null) {
            validatorAction.setAlert(this);
        }
        this.validatorAction = validatorAction;
    }

    public Alert validatorAction(ValidatorAction validatorAction) {
        this.setValidatorAction(validatorAction);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        if (this.event != null) {
            this.event.setAlert(null);
        }
        if (event != null) {
            event.setAlert(this);
        }
        this.event = event;
    }

    public Alert event(Event event) {
        this.setEvent(event);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alert)) {
            return false;
        }
        return getId() != null && getId().equals(((Alert) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alert{" +
            "id=" + getId() +
            ", alertType='" + getAlertType() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
