package com.piekoszek.nowaksharedrent.jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateToken(JwtData jwtData);
    void validateToken(String token);
    Claims readToken(String token);
}
