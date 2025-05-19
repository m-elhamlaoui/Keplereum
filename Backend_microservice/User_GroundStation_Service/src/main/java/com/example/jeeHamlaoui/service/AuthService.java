package com.example.jeeHamlaoui.service;

import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.dto.LoginRequest;
import com.example.jeeHamlaoui.model.dto.LoginResponse;
import com.example.jeeHamlaoui.model.dto.UserDto;
import com.example.jeeHamlaoui.model.dto.UserMapper;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public AuthService(UserRepository userRepository, 
                      UserMapper userMapper,
                      AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = generateToken(user);
        setJwtCookie(response, token);

        return new LoginResponse(userMapper.toDto(user));
    }

    public LoginResponse register(User user,HttpServletResponse response) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);

        String token = generateToken(savedUser);
        setJwtCookie(response, token
        );
        return new LoginResponse(userMapper.toDto(savedUser));
    }
    private String generateToken(User user) {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    private void setJwtCookie(HttpServletResponse response, String token) {
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false) // Enable in production (HTTPS only)
                .path("/")
                .maxAge(jwtExpiration) // Expires in 'jwtExpiration' seconds
                .sameSite("Strict") // Prevent CSRF
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
    }
    public void performLogout(HttpServletResponse response) {
        // Clear the JWT cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
    }
} 