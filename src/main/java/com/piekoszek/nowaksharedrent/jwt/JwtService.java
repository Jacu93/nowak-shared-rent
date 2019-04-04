package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.dto.User;

public interface JwtService {

    String generateToken(User user);
    void validateToken(String token);
    JwtData readToken(String token);
}
