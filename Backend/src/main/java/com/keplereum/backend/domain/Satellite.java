package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keplereum.backend.domain.enumeration.SatelliteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Satellite.
 */
@Entity
@Table(name = "satellite")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "satellite")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Satellite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String name;

    @Column(name = "launch_date")
    private Instant launchDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    private SatelliteStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "satellite")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "satellite" }, allowSetters = true)
    private Set<Sensor> sensors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "satellite")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "satellite" }, allowSetters = true)
    private Set<SatelliteTrajectory> trajectories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "satellites" }, allowSetters = true)
    private SatelliteModel model;

    @JsonIgnoreProperties(value = { "satellite", "blocks", "blockTransactions" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "satellite")
    @org.springframework.data.annotation.Transient
    private NetworkNode networkNode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "satellites" }, allowSetters = true)
    private GroundStation groundStation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Satellite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Satellite name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLaunchDate() {
        return this.launchDate;
    }

    public Satellite launchDate(Instant launchDate) {
        this.setLaunchDate(launchDate);
        return this;
    }

    public void setLaunchDate(Instant launchDate) {
        this.launchDate = launchDate;
    }

    public SatelliteStatus getStatus() {
        return this.status;
    }

    public Satellite status(SatelliteStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SatelliteStatus status) {
        this.status = status;
    }

    public Set<Sensor> getSensors() {
        return this.sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        if (this.sensors != null) {
            this.sensors.forEach(i -> i.setSatellite(null));
        }
        if (sensors != null) {
            sensors.forEach(i -> i.setSatellite(this));
        }
        this.sensors = sensors;
    }

    public Satellite sensors(Set<Sensor> sensors) {
        this.setSensors(sensors);
        return this;
    }

    public Satellite addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setSatellite(this);
        return this;
    }

    public Satellite removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setSatellite(null);
        return this;
    }

    public Set<SatelliteTrajectory> getTrajectories() {
        return this.trajectories;
    }

    public void setTrajectories(Set<SatelliteTrajectory> satelliteTrajectories) {
        if (this.trajectories != null) {
            this.trajectories.forEach(i -> i.setSatellite(null));
        }
        if (satelliteTrajectories != null) {
            satelliteTrajectories.forEach(i -> i.setSatellite(this));
        }
        this.trajectories = satelliteTrajectories;
    }

    public Satellite trajectories(Set<SatelliteTrajectory> satelliteTrajectories) {
        this.setTrajectories(satelliteTrajectories);
        return this;
    }

    public Satellite addTrajectory(SatelliteTrajectory satelliteTrajectory) {
        this.trajectories.add(satelliteTrajectory);
        satelliteTrajectory.setSatellite(this);
        return this;
    }

    public Satellite removeTrajectory(SatelliteTrajectory satelliteTrajectory) {
        this.trajectories.remove(satelliteTrajectory);
        satelliteTrajectory.setSatellite(null);
        return this;
    }

    public SatelliteModel getModel() {
        return this.model;
    }

    public void setModel(SatelliteModel satelliteModel) {
        this.model = satelliteModel;
    }

    public Satellite model(SatelliteModel satelliteModel) {
        this.setModel(satelliteModel);
        return this;
    }

    public NetworkNode getNetworkNode() {
        return this.networkNode;
    }

    public void setNetworkNode(NetworkNode networkNode) {
        if (this.networkNode != null) {
            this.networkNode.setSatellite(null);
        }
        if (networkNode != null) {
            networkNode.setSatellite(this);
        }
        this.networkNode = networkNode;
    }

    public Satellite networkNode(NetworkNode networkNode) {
        this.setNetworkNode(networkNode);
        return this;
    }

    public GroundStation getGroundStation() {
        return this.groundStation;
    }

    public void setGroundStation(GroundStation groundStation) {
        this.groundStation = groundStation;
    }

    public Satellite groundStation(GroundStation groundStation) {
        this.setGroundStation(groundStation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Satellite)) {
            return false;
        }
        return getId() != null && getId().equals(((Satellite) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Satellite{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", launchDate='" + getLaunchDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
