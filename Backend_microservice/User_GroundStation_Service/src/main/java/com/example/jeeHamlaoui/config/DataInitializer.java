package com.example.jeeHamlaoui.config;

import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            // Check if default user exists
            if (!userRepository.findByEmail("admin@admin.com").isPresent()) {
                // Create Ground Station
                GroundStation groundStation = new GroundStation();
                groundStation.setGroundStation_Name("Default Ground Station");
                groundStation.setGroundStation_Email("station@admin.com");
                groundStation.setGroundStation_AccesLevel(1);
                groundStation.setGroundStation_Latitude(36.8065);  // Example coordinates
                groundStation.setGroundStation_Longitude(10.1815); // for Tunis
                groundStation.setGroundStation_Description("Default ground station for system initialization");

                // Create User
                User defaultUser = new User();
                defaultUser.setUser_name("admin");
                defaultUser.setEmail("admin@admin.com");
                defaultUser.setPassword(passwordEncoder.encode("admin")); // Default password: admin
                defaultUser.setStatus(UserStatus.ACTIVE);
                defaultUser.setGroundStation(groundStation);
                
                // Save user (this will also save the ground station due to cascade)
                userRepository.save(defaultUser);
                
                System.out.println("Default user and ground station created:");
                System.out.println("Email: admin@admin.com");
                System.out.println("Password: admin");
            }
        };
    }
} 