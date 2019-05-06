package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.dto.User;
import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;
    private UserService userService;
    private HashService hashService;
    private JwtService jwtService;

    @Override
    public Optional<String> createAccount(Account account) {
        if(!accountRepository.existsByEmail(account.getEmail())) {
            String hashPassword = hashService.encrypt(account.getPassword());
            Account accountToCreate = new Account(account.getEmail(), account.getName(), hashPassword);
            User userToCreate = User.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .apartments(new HashSet<>())
                    .build();
            accountRepository.save(accountToCreate);
            userService.createUser(userToCreate);
            return Optional.of(jwtService.generateToken(userToCreate));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> loginUser(Account account) {
        Account registeredAccount = accountRepository.findByEmail(account.getEmail());
        if (registeredAccount != null && hashService.compareWithHash(account.getPassword(), registeredAccount.getPassword()))
        {
            return Optional.of(jwtService.generateToken(userService.getUser(account.getEmail())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> refreshToken(String oldToken) {
        try {
            jwtService.validateToken(oldToken);
            JwtData oldTokenData = jwtService.readToken(oldToken);
            User updatedData = userService.getUser(oldTokenData.getEmail());
            return Optional.of(jwtService.updateTokenData(oldTokenData, updatedData));
        }
        catch (Exception e) {
            log.error("Invalid token: " + e.getMessage());
            return Optional.empty();
        }
    }
}