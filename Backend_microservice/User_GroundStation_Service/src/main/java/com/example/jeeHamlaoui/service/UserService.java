package com.example.jeeHamlaoui.service;


import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.dto.UserDto;
import com.example.jeeHamlaoui.model.dto.UserMapper;
import com.example.jeeHamlaoui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Save or update a user
    public UserDto saveUser(User user) {
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // Get a user by ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Get a user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete a user by ID
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUser_name(updatedUser.getUser_name());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setStatus(updatedUser.getStatus());
                    existingUser.setGroundStation(updatedUser.getGroundStation());
                    // Only update password if you decide it's safe
                    existingUser.setPassword(updatedUser.getPassword());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    // Get all users
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

}
