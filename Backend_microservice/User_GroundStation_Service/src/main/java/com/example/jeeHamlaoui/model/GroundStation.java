package com.example.jeeHamlaoui.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.HashSet;

@Entity
public class GroundStation {
    @Id
    @GeneratedValue
    private Long groundStation_id;
    private String groundStation_Name;

    @Email
    private String groundStation_Email;

    private Integer groundStation_AccesLevel;

    private Double groundStation_Latitude;
    private Double groundStation_Longitude;


    private String groundStation_Description;

    @JsonBackReference
    @OneToOne(mappedBy = "groundStation")
    private User user;
    /*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groundStation")
    @JsonIgnoreProperties(value = { "sensors", "trajectories", "model", "networkNode", "groundStation" }, allowSetters = true)
    private Set<Satellite> satellites = new HashSet<>();
    */

    public GroundStation() {
    }

    public GroundStation(Long groundStation_id, String groundStation_Name, String groundStation_Email, Integer groundStation_AccesLevel, Double groundStation_Latitude, Double groundStation_Longitude, String groundStation_Description, User user) {//, Set<Satellite> satellites
        this.groundStation_id = groundStation_id;
        this.groundStation_Name = groundStation_Name;
        this.groundStation_Email = groundStation_Email;
        this.groundStation_AccesLevel = groundStation_AccesLevel;
        this.groundStation_Latitude = groundStation_Latitude;
        this.groundStation_Longitude = groundStation_Longitude;
        this.groundStation_Description = groundStation_Description;
        this.user = user;
        //this.satellites = satellites;
    }

    public Long getGroundStation_id() {
        return groundStation_id;
    }

    public void setGroundStation_id(Long groundStation_id) {
        this.groundStation_id = groundStation_id;
    }

    public String getGroundStation_Name() {
        return groundStation_Name;
    }

    public void setGroundStation_Name(String groundStation_Name) {
        this.groundStation_Name = groundStation_Name;
    }

    public @Email String getGroundStation_Email() {
        return groundStation_Email;
    }

    public void setGroundStation_Email(@Email String groundStation_Email) {
        this.groundStation_Email = groundStation_Email;
    }

    public Integer getGroundStation_AccesLevel() {
        return groundStation_AccesLevel;
    }

    public void setGroundStation_AccesLevel(Integer groundStation_AccesLevel) {
        this.groundStation_AccesLevel = groundStation_AccesLevel;
    }

    public Double getGroundStation_Longitude() {
        return groundStation_Longitude;
    }

    public void setGroundStation_Longitude(Double groundStation_Longitude) {
        this.groundStation_Longitude = groundStation_Longitude;
    }

    public Double getGroundStation_Latitude() {
        return groundStation_Latitude;
    }

    public void setGroundStation_Latitude(Double groundStation_Latitude) {
        this.groundStation_Latitude = groundStation_Latitude;
    }

    public String getGroundStation_Description() {
        return groundStation_Description;
    }

    public void setGroundStation_Description(String groundStation_Description) {
        this.groundStation_Description = groundStation_Description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
    public Set<Satellite> getSatellites() {
        return satellites;
    }

    public void setSatellites(Set<Satellite> satellites) {
        this.satellites = satellites;
    }

     */
}
