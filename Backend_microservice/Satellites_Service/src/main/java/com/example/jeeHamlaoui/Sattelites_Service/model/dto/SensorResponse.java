package com.example.jeeHamlaoui.Sattelites_Service.model.dto;

import com.example.jeeHamlaoui.Sattelites_Service.model.Sensor;
import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SensorActivity;

public class SensorResponse {
    private Long id;
    private String type;
    private Double maxHeight;
    private Double maxRadius;
    private SensorActivity activity;
    private Long satelliteId; // Only ID, not full object

    // Constructor from Sensor entity
    public SensorResponse(Sensor sensor) {
        this.id = sensor.getId();
        this.type = sensor.getType();
        this.maxHeight = sensor.getMaxHeight();
        this.maxRadius = sensor.getMaxRadius();
        this.activity = sensor.getActivity();
        this.satelliteId = sensor.getSatellite().getId();
    }

    public SensorResponse() {
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

    public SensorActivity getActivity() {
        return activity;
    }

    public void setActivity(SensorActivity activity) {
        this.activity = activity;
    }

    public Double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(Double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    // Getters (no setters needed)
}