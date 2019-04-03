package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.dto.User;
import com.piekoszek.nowaksharedrent.dto.UserRepository;
import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;

import java.util.Optional;

class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private HashService hashService;
    private JwtService jwtService;

    AuthServiceImpl (AccountRepository accountRepository, UserRepository userRepository, HashService hashService, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.hashService = hashService;
        this.jwtService = jwtService;
    }

    private String returnToken (User user) {
        JwtData jwtData = JwtData.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return jwtService.generateToken(jwtData);
    }

    @Override
    public Optional<String> createAccount(Account account) {
        if(!accountRepository.existsByEmail(account.getEmail())) {
            String hashPassword = hashService.encrypt(account.getPassword());
            Account accountToCreate = new Account(account.getEmail(), account.getName(), hashPassword);
            User userToCreate = new User(account.getEmail(), account.getName());
            accountRepository.save(accountToCreate);
            userRepository.save(userToCreate);
            return Optional.of(returnToken(userToCreate));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> loginUser(Account account) {
        Account registeredAccount = accountRepository.findByEmail(account.getEmail());
        if (registeredAccount != null && hashService.compareWithHash(account.getPassword(), registeredAccount.getPassword()))
        {
            return Optional.of(returnToken(userRepository.findByEmail(account.getEmail())));
        }
        return Optional.empty();
    }
}