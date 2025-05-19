package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SatelliteModel.
 */
@Entity
@Table(name = "satellite_model")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "satellitemodel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SatelliteModel implements Serializable {

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

    @NotNull
    @Column(name = "manufacturer", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String manufacturer;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "dimensions")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String dimensions;

    @Column(name = "power_capacity")
    private Double powerCapacity;

    @Column(name = "expected_lifespan")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer expectedLifespan;

    @Column(name = "design_trajectory_prediction_factor")
    private Double designTrajectoryPredictionFactor;

    @Column(name = "launch_mass")
    private Double launchMass;

    @Column(name = "dry_mass")
    private Double dryMass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "sensors", "trajectories", "model", "networkNode", "groundStation" }, allowSetters = true)
    private Set<Satellite> satellites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SatelliteModel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SatelliteModel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public SatelliteModel manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getWeight() {
        return this.weight;
    }

    public SatelliteModel weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public SatelliteModel dimensions(String dimensions) {
        this.setDimensions(dimensions);
        return this;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Double getPowerCapacity() {
        return this.powerCapacity;
    }

    public SatelliteModel powerCapacity(Double powerCapacity) {
        this.setPowerCapacity(powerCapacity);
        return this;
    }

    public void setPowerCapacity(Double powerCapacity) {
        this.powerCapacity = powerCapacity;
    }

    public Integer getExpectedLifespan() {
        return this.expectedLifespan;
    }

    public SatelliteModel expectedLifespan(Integer expectedLifespan) {
        this.setExpectedLifespan(expectedLifespan);
        return this;
    }

    public void setExpectedLifespan(Integer expectedLifespan) {
        this.expectedLifespan = expectedLifespan;
    }

    public Double getDesignTrajectoryPredictionFactor() {
        return this.designTrajectoryPredictionFactor;
    }

    public SatelliteModel designTrajectoryPredictionFactor(Double designTrajectoryPredictionFactor) {
        this.setDesignTrajectoryPredictionFactor(designTrajectoryPredictionFactor);
        return this;
    }

    public void setDesignTrajectoryPredictionFactor(Double designTrajectoryPredictionFactor) {
        this.designTrajectoryPredictionFactor = designTrajectoryPredictionFactor;
    }

    public Double getLaunchMass() {
        return this.launchMass;
    }

    public SatelliteModel launchMass(Double launchMass) {
        this.setLaunchMass(launchMass);
        return this;
    }

    public void setLaunchMass(Double launchMass) {
        this.launchMass = launchMass;
    }

    public Double getDryMass() {
        return this.dryMass;
    }

    public SatelliteModel dryMass(Double dryMass) {
        this.setDryMass(dryMass);
        return this;
    }

    public void setDryMass(Double dryMass) {
        this.dryMass = dryMass;
    }

    public Set<Satellite> getSatellites() {
        return this.satellites;
    }

    public void setSatellites(Set<Satellite> satellites) {
        if (this.satellites != null) {
            this.satellites.forEach(i -> i.setModel(null));
        }
        if (satellites != null) {
            satellites.forEach(i -> i.setModel(this));
        }
        this.satellites = satellites;
    }

    public SatelliteModel satellites(Set<Satellite> satellites) {
        this.setSatellites(satellites);
        return this;
    }

    public SatelliteModel addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
        satellite.setModel(this);
        return this;
    }

    public SatelliteModel removeSatellite(Satellite satellite) {
        this.satellites.remove(satellite);
        satellite.setModel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SatelliteModel)) {
            return false;
        }
        return getId() != null && getId().equals(((SatelliteModel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SatelliteModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", weight=" + getWeight() +
            ", dimensions='" + getDimensions() + "'" +
            ", powerCapacity=" + getPowerCapacity() +
            ", expectedLifespan=" + getExpectedLifespan() +
            ", designTrajectoryPredictionFactor=" + getDesignTrajectoryPredictionFactor() +
            ", launchMass=" + getLaunchMass() +
            ", dryMass=" + getDryMass() +
            "}";
    }
}
