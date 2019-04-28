package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.dto.User;
import com.piekoszek.nowaksharedrent.dto.UserApartment;
import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException;
import com.piekoszek.nowaksharedrent.time.TimeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.util.*;

@Setter
@AllArgsConstructor
class JwtServiceImpl implements JwtService {

    private SecretKey key;
    private TimeService timeService;

    @Override
    public String generateToken (User user) {
        Date now = new Date(timeService.millisSinceEpoch());

        String token = Jwts.builder()
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("apartments", user.getApartments())
                .setIssuedAt(now)
                .signWith(key)
                .setExpiration(new Date(now.getTime() + 60 * 60 * 1000))
                .compact();
        return ("bearer " + token);
    }

    @Override
    public String updateTokenData(JwtData jwtData, User userData) {
        Date now = new Date(timeService.millisSinceEpoch());

        String token = Jwts.builder()
                .claim("name", userData.getName())
                .claim("email", userData.getEmail())
                .claim("apartments", userData.getApartments())
                .setIssuedAt(now)
                .signWith(key)
                .setExpiration(jwtData.getExp())
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
                .exp(claims.getExpiration())
                .apartments(claimToHashSet(claims, "apartments"))
                .build();
    }

    private Set<UserApartment> claimToHashSet(Claims claims, String name) {
        Set<UserApartment> apartments = new HashSet<>();

        if (!claims.containsKey(name)) {
            return apartments;
        }

        try {
            List<UserApartment> apartmentsAsList = (ArrayList<UserApartment>) claims.get(name);
            apartments.addAll(apartmentsAsList);
            return apartments;
        }
        catch (Throwable e) {
            return apartments;
        }
    }

    private String claimToString(Claims claims, String name) {
        if (!claims.containsKey(name)) {
            return "";
        }
        return claims.get(name, String.class);
    }

    private String removeBearerString(String token) {
        if ((token.split(" ").length < 2) || (!token.toLowerCase().startsWith("bearer "))) {
            throw new InvalidTokenException("Expected bearer authorization type!");
        }
        return token.split(" ")[1];
    }
}