package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.UserRepository;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentServiceConfiguration {

    @Bean
    ApartmentService apartmentService (ApartmentRepository apartmentRepository, UserRepository userRepository, UuidService uuidService) {
        return new ApartmentServiceImpl(apartmentRepository, userRepository, uuidService);
    }
}
