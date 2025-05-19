package com.example.jeeHamlaoui.Sattelites_Service.model.dto;

import lombok.Data;

import java.time.Instant;


public class SatelliteTrajectoryRequest {
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
    private Long satelliteId; // Only ID, not full Satellite object

    // Getters and Setters
    // (Use Lombok @Data or generate manually)

    public SatelliteTrajectoryRequest() {
    }

    public SatelliteTrajectoryRequest(String status, Long satelliteId, String changeReason, Double orbitPeriapsis, Double orbitMeanAnomaly, Double orbitArgumentOfPerigee, Double orbitRightAscension, Double orbitInclination, Double orbitEccentricity, Instant endTime, Instant startTime) {
        this.status = status;
        this.satelliteId = satelliteId;
        this.changeReason = changeReason;
        this.orbitPeriapsis = orbitPeriapsis;
        this.orbitMeanAnomaly = orbitMeanAnomaly;
        this.orbitArgumentOfPerigee = orbitArgumentOfPerigee;
        this.orbitRightAscension = orbitRightAscension;
        this.orbitInclination = orbitInclination;
        this.orbitEccentricity = orbitEccentricity;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}