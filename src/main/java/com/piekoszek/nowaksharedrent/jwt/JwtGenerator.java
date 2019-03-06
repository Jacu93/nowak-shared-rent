package com.piekoszek.nowaksharedrent.jwt;

public interface JwtGenerator {

    String generateToken (JwtData jwtData);
}
