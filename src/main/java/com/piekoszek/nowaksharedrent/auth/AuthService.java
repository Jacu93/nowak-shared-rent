package com.piekoszek.nowaksharedrent.auth;

public interface AuthService {

    boolean createAccount(Account account);
    String loginUser(Account input);
}
