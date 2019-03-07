package com.piekoszek.nowaksharedrent.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthServiceConfiguration {

    AuthService authService() {
        return new AuthServiceImpl(new InMemoryAccountRepository());
    }

    @Bean
    AuthService authService(AccountRepository accountRepository) {
        return new AuthServiceImpl(accountRepository);
    }
}
