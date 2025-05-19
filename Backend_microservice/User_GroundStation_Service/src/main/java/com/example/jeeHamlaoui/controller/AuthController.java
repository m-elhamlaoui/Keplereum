package com.example.jeeHamlaoui.controller;

import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.dto.LoginRequest;
import com.example.jeeHamlaoui.model.dto.LoginResponse;
import com.example.jeeHamlaoui.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginresponse = authService.login(loginRequest,response);
        return ResponseEntity.ok(loginresponse);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody User user,HttpServletResponse response) {
        LoginResponse registerResponse = authService.register(user,response);
        return ResponseEntity.ok(registerResponse);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.performLogout(response);
        return ResponseEntity.ok("Logged out successfully");
    }

} 