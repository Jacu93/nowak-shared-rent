package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.time.TimeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.util.Date;

@Setter

class JwtServiceImpl implements JwtService {

    private SecretKey key;
    private TimeService timeService;

    public JwtServiceImpl(SecretKey key, TimeService timeService) {
        this.key = key;
        this.timeService = timeService;
    }

    @Override
    public String generateToken (JwtData jwtData) {
        Date now = new Date(timeService.millisSinceEpoch());

        return Jwts.builder()
                .claim("name", jwtData.getName())
                .claim("email", jwtData.getEmail())
                .setIssuedAt(now)
                .signWith(key)
                .setExpiration(new Date(now.getTime() + 60 * 60 * 1000))
                .compact();
    }

    @Override
    public void validateToken(String token) {
        Jwts.parser()
                .setClock(() -> new Date(timeService.millisSinceEpoch()))
                .setSigningKey(key)
                .parseClaimsJws(token);
    }

    @Override
    public Claims readToken(String token) {
        return Jwts.parser()
                .setClock(() -> new Date(timeService.millisSinceEpoch()))
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}