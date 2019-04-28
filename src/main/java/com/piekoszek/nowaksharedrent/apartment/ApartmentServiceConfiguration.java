package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentServiceConfiguration {

    ApartmentService apartmentService (UuidService uuidService, UserService userService) {
        return new ApartmentServiceImpl(new InMemoryApartmentRepository(), userService, uuidService);
    }

    @Bean
    ApartmentService apartmentService (ApartmentRepository apartmentRepository, UserService userService, UuidService uuidService) {
        return new ApartmentServiceImpl(apartmentRepository, userService, uuidService);
    }
}
