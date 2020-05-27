package com.piekoszek.nowaksharedrent.auth;

import java.util.Optional;

public interface AuthService {

    Optional<String> createAccount(Account account);
    Optional<String> loginUser(Account account);
    Optional<String> refreshToken(String oldToken);
    void resetPassword(String email);
    void setPassword(Account account);
}
