package com.example.jeeHamlaoui.Blockchain_Service.model;

import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.AlertType;
import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
@DiscriminatorValue("ALERT")
public class Alert extends AbstractTransactionType {

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false)
    private AlertType alertType;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    public Alert() {
    }

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    public Alert(AlertType alertType, Double longitude, Double latitude) {
        this.alertType = alertType;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    // Getters and setters
}