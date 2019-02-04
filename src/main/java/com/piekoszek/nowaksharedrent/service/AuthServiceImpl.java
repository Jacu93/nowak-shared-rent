package com.piekoszek.nowaksharedrent.service;

import com.piekoszek.nowaksharedrent.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private static Map<String, Account> accountRepository = new HashMap<>();

    @Override
    public void createAccount(Account account) {
        accountRepository.put(account.getId(), account);
    }
}
