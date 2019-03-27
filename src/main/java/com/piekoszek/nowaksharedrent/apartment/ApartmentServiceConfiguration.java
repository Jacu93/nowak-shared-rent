package com.piekoszek.nowaksharedrent.apartment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentServiceConfiguration {

    @Bean
    ApartmentService apartmentService (ApartmentRepository apartmentRepository) {
        return new ApartmentServiceImpl(apartmentRepository);
    }
}
