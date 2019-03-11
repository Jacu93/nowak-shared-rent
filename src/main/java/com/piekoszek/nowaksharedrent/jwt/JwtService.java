package com.piekoszek.nowaksharedrent.jwt;

public interface JwtService {

    String generateToken(JwtData jwtData);
    void validateToken(String token);
    JwtData readToken(String token);
}
