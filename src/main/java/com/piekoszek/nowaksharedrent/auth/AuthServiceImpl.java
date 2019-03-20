package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;

import java.util.Optional;

class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;
    private HashService hashService;
    private JwtService jwtService;

    AuthServiceImpl (AccountRepository accountRepository, HashService hashService, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.hashService = hashService;
        this.jwtService = jwtService;
    }

    private String returnToken (Account account) {
        JwtData jwtData = JwtData.builder()
                .email(account.getEmail())
                .name(account.getName())
                .build();
        return jwtService.generateToken(jwtData);
    }

    @Override
    public Optional<String> createAccount(Account account) {
        if(!accountRepository.existsByEmail(account.getEmail())) {
            String hashPassword = hashService.encrypt(account.getPassword());
            Account accountToCreate = new Account(account.getEmail(), account.getName(), hashPassword);
            accountRepository.save(accountToCreate);
            return Optional.of(returnToken(accountToCreate));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> loginUser(Account account) {
        Account registeredAccount = accountRepository.findByEmail(account.getEmail());
        if (registeredAccount != null && hashService.compareWithHash(account.getPassword(), registeredAccount.getPassword()))
        {
            return Optional.of(returnToken(registeredAccount));
        }
        return Optional.empty();
    }
}