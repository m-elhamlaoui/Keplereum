package com.example.apiGateway.apiGateway.util;

import com.example.apiGateway.apiGateway.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class JwtUtil {
    private final JwtConfig jwtConfig;
    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));

        String[] parts = token.split("\\.");
        if (parts.length >= 1) {
            String headerJson = new String(Base64.getDecoder().decode(parts[0]));
            log.info("Token header: {}", headerJson);
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Token claims: {}", claims);
            return true;
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            return false;
        }
    }

} 