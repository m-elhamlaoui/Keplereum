package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A SatelliteTrajectory.
 */
@Entity
@Table(name = "satellite_trajectory")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "satellitetrajectory")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SatelliteTrajectory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String status;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "orbit_eccentricity")
    private Double orbitEccentricity;

    @Column(name = "orbit_inclination")
    private Double orbitInclination;

    @Column(name = "orbit_right_ascension")
    private Double orbitRightAscension;

    @Column(name = "orbit_argument_of_perigee")
    private Double orbitArgumentOfPerigee;

    @Column(name = "orbit_mean_anomaly")
    private Double orbitMeanAnomaly;

    @Column(name = "orbit_periapsis")
    private Double orbitPeriapsis;

    @Column(name = "change_reason")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String changeReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sensors", "trajectories", "model", "networkNode", "groundStation" }, allowSetters = true)
    private Satellite satellite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SatelliteTrajectory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public SatelliteTrajectory status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public SatelliteTrajectory startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public SatelliteTrajectory endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Double getOrbitEccentricity() {
        return this.orbitEccentricity;
    }

    public SatelliteTrajectory orbitEccentricity(Double orbitEccentricity) {
        this.setOrbitEccentricity(orbitEccentricity);
        return this;
    }

    public void setOrbitEccentricity(Double orbitEccentricity) {
        this.orbitEccentricity = orbitEccentricity;
    }

    public Double getOrbitInclination() {
        return this.orbitInclination;
    }

    public SatelliteTrajectory orbitInclination(Double orbitInclination) {
        this.setOrbitInclination(orbitInclination);
        return this;
    }

    public void setOrbitInclination(Double orbitInclination) {
        this.orbitInclination = orbitInclination;
    }

    public Double getOrbitRightAscension() {
        return this.orbitRightAscension;
    }

    public SatelliteTrajectory orbitRightAscension(Double orbitRightAscension) {
        this.setOrbitRightAscension(orbitRightAscension);
        return this;
    }

    public void setOrbitRightAscension(Double orbitRightAscension) {
        this.orbitRightAscension = orbitRightAscension;
    }

    public Double getOrbitArgumentOfPerigee() {
        return this.orbitArgumentOfPerigee;
    }

    public SatelliteTrajectory orbitArgumentOfPerigee(Double orbitArgumentOfPerigee) {
        this.setOrbitArgumentOfPerigee(orbitArgumentOfPerigee);
        return this;
    }

    public void setOrbitArgumentOfPerigee(Double orbitArgumentOfPerigee) {
        this.orbitArgumentOfPerigee = orbitArgumentOfPerigee;
    }

    public Double getOrbitMeanAnomaly() {
        return this.orbitMeanAnomaly;
    }

    public SatelliteTrajectory orbitMeanAnomaly(Double orbitMeanAnomaly) {
        this.setOrbitMeanAnomaly(orbitMeanAnomaly);
        return this;
    }

    public void setOrbitMeanAnomaly(Double orbitMeanAnomaly) {
        this.orbitMeanAnomaly = orbitMeanAnomaly;
    }

    public Double getOrbitPeriapsis() {
        return this.orbitPeriapsis;
    }

    public SatelliteTrajectory orbitPeriapsis(Double orbitPeriapsis) {
        this.setOrbitPeriapsis(orbitPeriapsis);
        return this;
    }

    public void setOrbitPeriapsis(Double orbitPeriapsis) {
        this.orbitPeriapsis = orbitPeriapsis;
    }

    public String getChangeReason() {
        return this.changeReason;
    }

    public SatelliteTrajectory changeReason(String changeReason) {
        this.setChangeReason(changeReason);
        return this;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Satellite getSatellite() {
        return this.satellite;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }

    public SatelliteTrajectory satellite(Satellite satellite) {
        this.setSatellite(satellite);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SatelliteTrajectory)) {
            return false;
        }
        return getId() != null && getId().equals(((SatelliteTrajectory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SatelliteTrajectory{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", orbitEccentricity=" + getOrbitEccentricity() +
            ", orbitInclination=" + getOrbitInclination() +
            ", orbitRightAscension=" + getOrbitRightAscension() +
            ", orbitArgumentOfPerigee=" + getOrbitArgumentOfPerigee() +
            ", orbitMeanAnomaly=" + getOrbitMeanAnomaly() +
            ", orbitPeriapsis=" + getOrbitPeriapsis() +
            ", changeReason='" + getChangeReason() + "'" +
            "}";
    }
}
