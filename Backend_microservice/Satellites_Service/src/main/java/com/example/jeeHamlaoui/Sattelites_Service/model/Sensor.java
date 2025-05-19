package com.example.jeeHamlaoui.Sattelites_Service.model;

import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SensorActivity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

/**
 * A Sensor.
 */
@Entity
@Table(name = "sensor")
public class Sensor {


    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "max_height")
    private Double maxHeight;

    @Column(name = "max_radius")
    private Double maxRadius;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activity", nullable = false)
    private SensorActivity activity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "satellite_id")
    private Satellite satellite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sensor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Sensor type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMaxHeight() {
        return this.maxHeight;
    }

    public Sensor maxHeight(Double maxHeight) {
        this.setMaxHeight(maxHeight);
        return this;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Double getMaxRadius() {
        return this.maxRadius;
    }

    public Sensor maxRadius(Double maxRadius) {
        this.setMaxRadius(maxRadius);
        return this;
    }

    public void setMaxRadius(Double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public SensorActivity getActivity() {
        return this.activity;
    }

    public Sensor activity(SensorActivity activity) {
        this.setActivity(activity);
        return this;
    }

    public void setActivity(SensorActivity activity) {
        this.activity = activity;
    }

    public Satellite getSatellite() {
        return this.satellite;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }

    public Sensor satellite(Satellite satellite) {
        this.setSatellite(satellite);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sensor)) {
            return false;
        }
        return getId() != null && getId().equals(((Sensor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", maxHeight=" + getMaxHeight() +
            ", maxRadius=" + getMaxRadius() +
            ", activity='" + getActivity() + "'" +
            "}";
    }
}
