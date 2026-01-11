package com.example.jeeHamlaoui.Sattelites_Service.model;

import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SatelliteStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import org.antlr.v4.runtime.misc.NotNull;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Satellite.
 */
@Entity
@Table(name = "satellite")
public class Satellite {


    @Id
    @GeneratedValue
    @Column(name = "satellite_id")
    private Long satellite_id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "launch_date")
    private Instant launchDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SatelliteStatus status;

    @OneToMany( mappedBy = "satellite")
    @JsonManagedReference
    private Set<Sensor> sensors = new HashSet<>();

    @OneToMany(mappedBy = "satellite")
    @JsonManagedReference
    private Set<SatelliteTrajectory> trajectories = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SatelliteModelId",referencedColumnName = "SatelliteModelId")
    private SatelliteModel model;


    private String networkNodeId;


    private Long groundStationId;

    public String getNetworkNodeId() {
        return networkNodeId;
    }

    public void setNetworkNodeId(String networkNodeId) {
        this.networkNodeId = networkNodeId;
    }

    public Long getGroundStationId() {
        return groundStationId;
    }

    public void setGroundStationId(Long groundStationId) {
        this.groundStationId = groundStationId;
    }

    public Long getSatellite_id() {
        return satellite_id;
    }

    public void setSatellite_id(Long satellite_id) {
        this.satellite_id = satellite_id;
    }

    public Long getId() {
        return this.satellite_id;
    }

    public Satellite satellite_id(Long satellite_id) {
        this.setId(satellite_id);
        return this;
    }

    public void setId(Long satellite_id) {
        this.satellite_id = satellite_id;
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
            "satellite_id=" + getId() +
            ", name='" + getName() + "'" +
            ", launchDate='" + getLaunchDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
