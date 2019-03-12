package com.piekoszek.nowaksharedrent.auth;

import java.util.Optional;

public interface AuthService {

    boolean createAccount(Account account);
    Optional<String> loginUser(Account input);
}
