package com.example.jeeHamlaoui.repository;

import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);


}
