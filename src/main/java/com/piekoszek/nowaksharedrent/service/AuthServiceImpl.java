package com.piekoszek.nowaksharedrent.service;

import com.piekoszek.nowaksharedrent.model.Account;
import com.piekoszek.nowaksharedrent.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private AccountRepository accountRepository;

    public AuthServiceImpl (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean createAccount(Account account) {
        if(!accountRepository.existsById(account.getEmail())) {
            accountRepository.save(
                    Account.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .password(account.getPassword())
                    .build()
            );
            return true;
        }
        return false;
    }

    @Override
    public List<Account> getAccount() {
        return accountRepository.findAll();
    }
}
