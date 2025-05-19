package com.example.jeeHamlaoui.Blockchain_Service.model.dto;



import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.AlertType;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;

public class AlertDTO extends TransactionTypeDTO{
    private AlertType alertType;
    private Double latitude;
    private Double longitude;

    // Default constructor
    public AlertDTO() {
    }

    // All-args constructor
    public AlertDTO(AlertType alertType, Double latitude, Double longitude) {
        this.alertType = alertType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.ALERT;
    }
}