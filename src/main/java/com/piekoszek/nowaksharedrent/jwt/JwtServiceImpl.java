package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException;
import com.piekoszek.nowaksharedrent.time.TimeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.util.Date;

@Setter

class JwtServiceImpl implements JwtService {

    private SecretKey key;
    private TimeService timeService;

    JwtServiceImpl(SecretKey key, TimeService timeService) {
        this.key = key;
        this.timeService = timeService;
    }

    void setKey(SecretKey key) {
        this.key = key;
    }

    @Override
    public String generateToken (JwtData jwtData) {
        Date now = new Date(timeService.millisSinceEpoch());

        String token = Jwts.builder()
                .claim("name", jwtData.getName())
                .claim("email", jwtData.getEmail())
                .setIssuedAt(now)
                .signWith(key)
                .setExpiration(new Date(now.getTime() + 60 * 60 * 1000))
                .compact();
        return ("bearer " + token);
    }

    @Override
    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .setClock(() -> new Date(timeService.millisSinceEpoch()))
                    .setSigningKey(key)
                    .parseClaimsJws(removeBearerString(token));
        }
        catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token expired");
        }
        catch (MalformedJwtException e) {
            throw new InvalidTokenException("Invalid token format");
        }
        catch (SignatureException e) {
            throw new InvalidTokenException("Invalid token signature");
        }
    }

    @Override
    public JwtData readToken(String token) {
        Claims claims = Jwts.parser()
                .setClock(() -> new Date(timeService.millisSinceEpoch()))
                .setSigningKey(key)
                .parseClaimsJws(removeBearerString(token))
                .getBody();
        return JwtData.builder()
                .name(claimToString(claims, "name"))
                .email(claimToString(claims, "email"))
                .build();
    }

    private String claimToString(Claims claims, String name) {
        if (!claims.containsKey(name)) {
            return "";
        }
        return claims.get(name, String.class);
    }

    private String removeBearerString(String token) {
            if ((token.split(" ").length < 2) || (!token.toLowerCase().startsWith("bearer "))) {
            throw new MalformedJwtException("Expected bearer authorization type!");
        }
        return token.split(" ")[1];
    }
}