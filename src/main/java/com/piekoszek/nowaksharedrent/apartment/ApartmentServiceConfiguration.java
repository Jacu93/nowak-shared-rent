package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.auth.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentServiceConfiguration {

    @Bean
    ApartmentService apartmentService (ApartmentRepository apartmentRepository, AccountRepository accountRepository) {
        return new ApartmentServiceImpl(apartmentRepository, accountRepository);
    }
}
