package com.piekoszek.nowaksharedrent.service;

import com.piekoszek.nowaksharedrent.model.Account;

import java.util.List;

public interface AuthService {
    void createAccount(Account account);
    List<Account> getAccount();
}
