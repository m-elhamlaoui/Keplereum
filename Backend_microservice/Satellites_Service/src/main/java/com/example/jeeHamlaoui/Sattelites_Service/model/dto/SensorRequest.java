package com.example.jeeHamlaoui.Sattelites_Service.model.dto;

import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SensorActivity;

public class SensorRequest {
    private String type;
    private Double maxHeight;
    private Double maxRadius;
    private SensorActivity activity;
    private Long satelliteId; // Only the ID, not the full Satellite object



    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(Double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public SensorActivity getActivity() {
        return activity;
    }

    public void setActivity(SensorActivity activity) {
        this.activity = activity;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }
}