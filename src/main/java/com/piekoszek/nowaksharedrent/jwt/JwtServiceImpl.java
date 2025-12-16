package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException;
import com.piekoszek.nowaksharedrent.time.TimeService;
import com.piekoszek.nowaksharedrent.user.User;
import com.piekoszek.nowaksharedrent.user.UserApartment;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.util.*;

@Setter
@AllArgsConstructor
class JwtServiceImpl implements JwtService {

    private static final String BEARER_PREFIX = "bearer ";

    private SecretKey key;
    private TimeService timeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String generateToken(User user) {
        Date now = new Date(timeService.millisSinceEpoch());

        String token = Jwts.builder()
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("apartments", user.getApartments())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + 60L * 60L * 1000L))
                .signWith(key)
                .compact();
         return String.join(" ", BEARER_PREFIX, token);
    }

    @Override
    public String updateTokenData(JwtData jwtData, User userData) {
        Date now = new Date(timeService.millisSinceEpoch());

        String token = Jwts.builder()
                .claim("name", userData.getName())
                .claim("email", userData.getEmail())
                .claim("apartments", userData.getApartments())
                .issuedAt(now)
                .expiration(jwtData.getExp())
                .signWith(key)
                .compact();
        return String.join(" ", BEARER_PREFIX, token);
    }

    @Override
    public void validateToken(String token) {
        try {
            parser().parseSignedClaims(removeBearerString(token));
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token expired");
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException("Invalid token format");
        } catch (SignatureException e) {
            throw new InvalidTokenException("Invalid token signature");
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token");
        }
    }

    @Override
    public JwtData readToken(String token) {
        Claims claims;
        try {
            claims = parser()
                    .parseSignedClaims(removeBearerString(token))
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token");
        }

        return JwtData.builder()
                .name(claimToString(claims, "name"))
                .email(claimToString(claims, "email"))
                .exp(claims.getExpiration())
                .apartments(claimToApartments(claims))
                .build();
    }

    private JwtParser parser() {
        return Jwts.parser()
                .clock(() -> new Date(timeService.millisSinceEpoch()))
                .verifyWith(key)
                .build();
    }

    private Set<UserApartment> claimToApartments(Claims claims) {
        if (!claims.containsKey("apartments")) {
            return new HashSet<>();
        }

        Object raw = claims.get("apartments");
        if (raw == null) {
            return new HashSet<>();
        }

        try {
            UserApartment[] arr = objectMapper.convertValue(raw, UserApartment[].class);
            return new HashSet<>(Arrays.asList(arr));
        } catch (IllegalArgumentException ex) {
            return new HashSet<>();
        }
    }

    private String claimToString(Claims claims, String name) {
        if (!claims.containsKey(name)) {
            return "";
        }
        return claims.get(name, String.class);
    }

    private String removeBearerString(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Missing Authorization header!");
        }

        String trimmed = token.trim();
        if (trimmed.length() <= BEARER_PREFIX.length() || !trimmed.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            throw new InvalidTokenException("Expected bearer authorization type!");
        }

        return trimmed.substring(BEARER_PREFIX.length()).trim();
    }
}