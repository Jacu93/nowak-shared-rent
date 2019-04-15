package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.dto.User;

import java.util.Date;

public interface JwtService {

    String generateToken(User user);
    String generateToken (User user, Date exp);
    void validateToken(String token);
    JwtData readToken(String token);
}
