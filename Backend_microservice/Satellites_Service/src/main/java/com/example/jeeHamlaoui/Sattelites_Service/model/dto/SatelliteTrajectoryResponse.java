package com.example.jeeHamlaoui.Sattelites_Service.model.dto;

import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteTrajectory;

import java.time.Instant;

public class SatelliteTrajectoryResponse {
    private Long id;
    private String status;
    private Instant startTime;
    private Instant endTime;
    private Double orbitEccentricity;
    private Double orbitInclination;
    private Double orbitRightAscension;
    private Double orbitArgumentOfPerigee;
    private Double orbitMeanAnomaly;
    private Double orbitPeriapsis;
    private String changeReason;
    private Long satelliteId; // Only ID, no circular reference

    // Constructor from Entity
    public SatelliteTrajectoryResponse(SatelliteTrajectory trajectory) {
        this.id = trajectory.getId();
        this.status = trajectory.getStatus();
        this.startTime = trajectory.getStartTime();
        this.endTime = trajectory.getEndTime();
        this.orbitEccentricity = trajectory.getOrbitEccentricity();
        this.orbitInclination = trajectory.getOrbitInclination();
        this.orbitRightAscension = trajectory.getOrbitRightAscension();
        this.orbitArgumentOfPerigee = trajectory.getOrbitArgumentOfPerigee();
        this.orbitMeanAnomaly = trajectory.getOrbitMeanAnomaly();
        this.orbitPeriapsis = trajectory.getOrbitPeriapsis();
        this.changeReason = trajectory.getChangeReason();
        this.satelliteId = trajectory.getSatellite() != null ?
                trajectory.getSatellite().getId() : null;
    }

    // Getters (no setters needed for responses)

    public SatelliteTrajectoryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Double getOrbitPeriapsis() {
        return orbitPeriapsis;
    }

    public void setOrbitPeriapsis(Double orbitPeriapsis) {
        this.orbitPeriapsis = orbitPeriapsis;
    }

    public Double getOrbitMeanAnomaly() {
        return orbitMeanAnomaly;
    }

    public void setOrbitMeanAnomaly(Double orbitMeanAnomaly) {
        this.orbitMeanAnomaly = orbitMeanAnomaly;
    }

    public Double getOrbitArgumentOfPerigee() {
        return orbitArgumentOfPerigee;
    }

    public void setOrbitArgumentOfPerigee(Double orbitArgumentOfPerigee) {
        this.orbitArgumentOfPerigee = orbitArgumentOfPerigee;
    }

    public Double getOrbitRightAscension() {
        return orbitRightAscension;
    }

    public void setOrbitRightAscension(Double orbitRightAscension) {
        this.orbitRightAscension = orbitRightAscension;
    }

    public Double getOrbitInclination() {
        return orbitInclination;
    }

    public void setOrbitInclination(Double orbitInclination) {
        this.orbitInclination = orbitInclination;
    }

    public Double getOrbitEccentricity() {
        return orbitEccentricity;
    }

    public void setOrbitEccentricity(Double orbitEccentricity) {
        this.orbitEccentricity = orbitEccentricity;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}