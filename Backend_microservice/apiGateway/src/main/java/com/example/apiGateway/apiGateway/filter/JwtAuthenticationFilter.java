package com.example.apiGateway.apiGateway.filter;

import com.example.apiGateway.apiGateway.config.JwtConfig;
import com.example.apiGateway.apiGateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    public JwtAuthenticationFilter(JwtUtil jwtUtil, JwtConfig jwtConfig) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.jwtConfig = jwtConfig;

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            String token = extractToken(exchange.getRequest());

            if (token == null || !jwtUtil.validateToken(token)) {
                log.error("Token is missing or invalid for path: {}", path);

                if (path.contains("/auth/login") || path.contains("/auth/register")) {
                    return chain.filter(exchange);
                } else {
                    log.error("Blocking unauthorized access to: {}", path);
                    return onError(exchange, "Unauthorized: JWT required", HttpStatus.UNAUTHORIZED);
                }
            }

            log.info("Token is valid. Forwarding request to: {}", path);

            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header("Authorization", "Bearer " + token)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }



    private String extractToken(ServerHttpRequest request) {
        // 1. Check cookie
        if (request.getCookies().containsKey("jwt")) {
            return request.getCookies().getFirst("jwt").getValue();
        }
        // 2. Check Authorization header

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(jwtConfig.getPrefix())) {
            return authHeader.replace(jwtConfig.getPrefix(), "").trim();
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        log.error("JWT authentication error: {}", err);
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // Put configuration properties here if needed
    }
} 