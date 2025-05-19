package com.example.apiGateway.apiGateway.config;

import com.example.apiGateway.apiGateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Configuration
public class RouteConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final Logger log = LoggerFactory.getLogger(RouteConfig.class);

    @Autowired
    public RouteConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                // Auth routes (no authentication required)
                .route("user-auth-route", r -> r
                        .path("/api/v1/users/auth/**")
                        .uri("lb://USER-SERVICE"))
                
                // Protected User Service routes
                .route("user-service-route", r -> r
                        .path("/api/v1/users/**", "/api/v1/groundstations/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://USER-SERVICE"))
                
                // Protected Satellite Service routes
                .route("satellite-service-route", r -> r
                        .path("/api/v1/satellites/**", "/api/v1/models/**", 
                              "/api/v1/trajectories/**", "/api/v1/sensors/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://SATELLITE-SERVICE"))
                
                // Protected Blockchain Service routes
                .route("blockchain-service-route", r -> r
                        .path("/api/v1/blocks/**", "/api/v1/transactions/**", "/api/v1/nodes/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://BLOCKCHAIN-SERVICE"))
                .build();
    }
} 