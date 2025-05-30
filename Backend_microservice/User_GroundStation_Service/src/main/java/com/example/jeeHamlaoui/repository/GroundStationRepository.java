package com.example.jeeHamlaoui.repository;

import com.example.jeeHamlaoui.model.GroundStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroundStationRepository extends JpaRepository<GroundStation, Long> {
    Optional<GroundStation> findByUser_UserId(long userId);
}
