package com.example.jeeHamlaoui.model;

import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Builder
@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    private String user_name;
    private String password;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "groundStation_id")
    @JsonIgnoreProperties(value = "user",allowSetters = true)
    private GroundStation groundStation;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant created_at;

    public User(Long userId, String user_name, String password, String email, UserStatus status, GroundStation groundStation, Instant created_at) {
        this.userId = userId;
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.status = status;
        this.groundStation = groundStation;
        this.created_at = created_at;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public GroundStation getGroundStation() {
        return groundStation;
    }

    public void setGroundStation(GroundStation groundStation) {
        this.groundStation = groundStation;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}
