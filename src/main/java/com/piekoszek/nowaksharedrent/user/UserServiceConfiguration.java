package com.piekoszek.nowaksharedrent.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {

    UserService userService () {
        return new UserServiceImpl(new InMemoryUserRepository());
    }

    @Bean
    UserService userService (UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }
}
