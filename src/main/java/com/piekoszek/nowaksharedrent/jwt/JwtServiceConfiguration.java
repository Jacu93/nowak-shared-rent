package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.time.TimeService;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
class JwtServiceConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    JwtService jwtService(SecretKey secretKey, TimeService timeService) {
        return new JwtServiceImpl(secretKey, timeService);
    }

    @Bean
    JwtService jwtService(TimeService timeService) {
        return new JwtServiceImpl(Keys.hmacShaKeyFor(secret.getBytes()), timeService);
    }
}
