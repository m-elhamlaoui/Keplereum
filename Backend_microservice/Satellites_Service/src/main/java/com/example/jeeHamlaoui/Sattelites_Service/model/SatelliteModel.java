package com.example.jeeHamlaoui.Sattelites_Service.model;


import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "satellite_model")
public class SatelliteModel{

    @Id
    @GeneratedValue
    @Column(name = "SatelliteModelId")
    private Long SatelliteModelId;

    
    @Column(name = "name", nullable = false)
    private String name;

    
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "power_capacity")
    private Double powerCapacity;

    @Column(name = "expected_lifespan")
    private Integer expectedLifespan;

    @Column(name = "design_trajectory_prediction_factor")
    private Double designTrajectoryPredictionFactor;

    @Column(name = "launch_mass")
    private Double launchMass;

    @Column(name = "dry_mass")
    private Double dryMass;

    @OneToMany(mappedBy = "satellite_id")
    private Set<Satellite> satellite;


    public SatelliteModel() {
    }

    public SatelliteModel(String name, Double weight, String manufacturer, String dimensions, Double powerCapacity, Integer expectedLifespan, Double designTrajectoryPredictionFactor, Double launchMass, Double dryMass) {

        this.name = name;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.dimensions = dimensions;
        this.powerCapacity = powerCapacity;
        this.expectedLifespan = expectedLifespan;
        this.designTrajectoryPredictionFactor = designTrajectoryPredictionFactor;
        this.launchMass = launchMass;
        this.dryMass = dryMass;
    }


// jhipster-needle-entity-add-field - JHipster will add fields here


    public Long getSatelliteModel_id() {
        return SatelliteModelId;
    }

    public void setSatelliteModel_id(Long SatelliteModelId) {
        SatelliteModelId = SatelliteModelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Double getPowerCapacity() {
        return powerCapacity;
    }

    public void setPowerCapacity(Double powerCapacity) {
        this.powerCapacity = powerCapacity;
    }

    public Integer getExpectedLifespan() {
        return expectedLifespan;
    }

    public void setExpectedLifespan(Integer expectedLifespan) {
        this.expectedLifespan = expectedLifespan;
    }

    public Double getDesignTrajectoryPredictionFactor() {
        return designTrajectoryPredictionFactor;
    }

    public void setDesignTrajectoryPredictionFactor(Double designTrajectoryPredictionFactor) {
        this.designTrajectoryPredictionFactor = designTrajectoryPredictionFactor;
    }

    public Double getLaunchMass() {
        return launchMass;
    }

    public void setLaunchMass(Double launchMass) {
        this.launchMass = launchMass;
    }

    public Double getDryMass() {
        return dryMass;
    }

    public void setDryMass(Double dryMass) {
        this.dryMass = dryMass;
    }
}
