package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthServiceConfiguration {

    AuthService authService(HashService hashService, JwtService jwtService) {
        return new AuthServiceImpl(new InMemoryAccountRepository(), hashService, jwtService);
    }

    @Bean
    AuthService authService(AccountRepository accountRepository, HashService hashService, JwtService jwtService) {
        return new AuthServiceImpl(accountRepository, hashService, jwtService);
    }
}
