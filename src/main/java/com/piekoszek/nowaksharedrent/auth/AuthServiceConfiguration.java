package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthServiceConfiguration {

    AuthService authService(HashService hashService, UserService userService, JwtService jwtService) {
        return new AuthServiceImpl(new InMemoryAccountRepository(), userService, hashService, jwtService);
    }

    @Bean
    AuthService authService(AccountRepository accountRepository, UserService userService, HashService hashService, JwtService jwtService) {
        return new AuthServiceImpl(accountRepository, userService, hashService, jwtService);
    }
}
