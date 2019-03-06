package com.piekoszek.nowaksharedrent.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

@Setter

public class JwtGeneratorImpl implements JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;
    private TimeService timeService;

    @Override
    public String generateToken (JwtData jwtData) {
        Date now = new Date(timeService.millisSinceEpoch());
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .claim("name", jwtData.getName())
                .claim("email", jwtData.getEmail())
                .setIssuedAt(now)
                .signWith(key)
                .setExpiration(new Date(now.getTime() + 60 * 60 * 1000))
                .compact();
    }
}