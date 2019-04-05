package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.dto.InMemoryUserRepository;
import com.piekoszek.nowaksharedrent.dto.UserRepository;
import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthServiceConfiguration {

    AuthService authService(HashService hashService, JwtService jwtService) {
        return new AuthServiceImpl(new InMemoryAccountRepository(), new InMemoryUserRepository(), hashService, jwtService);
    }

    @Bean
    AuthService authService(AccountRepository accountRepository, UserRepository userRepository, HashService hashService, JwtService jwtService) {
        return new AuthServiceImpl(accountRepository, userRepository, hashService, jwtService);
    }
}
