package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.time.TimeService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApartmentServiceConfiguration {

    ApartmentService apartmentService (UuidService uuidService, UserService userService, TimeService timeService) {
        return new ApartmentServiceImpl(new InMemoryApartmentRepository(), userService, uuidService, timeService);
    }

    @Bean
    ApartmentService apartmentService (ApartmentRepository apartmentRepository, UserService userService, UuidService uuidService, TimeService timeService) {
        return new ApartmentServiceImpl(apartmentRepository, userService, uuidService, timeService);
    }
}
