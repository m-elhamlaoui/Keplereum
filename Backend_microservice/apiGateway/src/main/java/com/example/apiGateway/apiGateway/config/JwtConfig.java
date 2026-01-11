package com.example.apiGateway.apiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}