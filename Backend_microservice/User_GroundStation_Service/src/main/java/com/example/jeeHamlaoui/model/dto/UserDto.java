package com.example.jeeHamlaoui.model.dto;

import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import java.time.Instant;

public class UserDto {
    private Long userId;
    private String user_name;
    private String email;
    private UserStatus status;
    private GroundStation groundStation;
    private Instant created_at;

    public UserDto() {
    }

    public UserDto(Long userId, Instant created_at, GroundStation groundStation, UserStatus status, String email, String user_name) {
        this.userId = userId;
        this.created_at = created_at;
        this.groundStation = groundStation;
        this.status = status;
        this.email = email;
        this.user_name = user_name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
