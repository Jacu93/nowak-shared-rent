package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.email.EmailService;
import com.piekoszek.nowaksharedrent.user.UserService;
import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthServiceConfiguration {

    AuthService authService(UserService userService, HashService hashService, JwtService jwtService, EmailService emailService, UuidService uuidService) {
        return new AuthServiceImpl(new InMemoryAccountRepository(), userService, hashService, jwtService, emailService, uuidService);
    }

    @Bean
    AuthService authService(AccountRepository accountRepository, UserService userService, HashService hashService, JwtService jwtService, EmailService emailService, UuidService uuidService) {
        return new AuthServiceImpl(accountRepository, userService, hashService, jwtService, emailService, uuidService);
    }
}
