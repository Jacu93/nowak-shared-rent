package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentServiceConfiguration {

    @Bean
    ApartmentService apartmentService (ApartmentRepository apartmentRepository, UserRepository userRepository) {
        return new ApartmentServiceImpl(apartmentRepository, userRepository);
    }
}
