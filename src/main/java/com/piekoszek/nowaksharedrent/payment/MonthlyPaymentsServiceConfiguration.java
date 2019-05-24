package com.piekoszek.nowaksharedrent.payment;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonthlyPaymentsServiceConfiguration {

    @Bean
    MonthlyPaymentsService monthlyPaymentsService (MonthlyPaymentsRepository monthlyPaymentsRepository, ApartmentService apartmentService, UuidService uuidService) {
        return new MonthlyPaymentsServiceImpl(monthlyPaymentsRepository, apartmentService, uuidService);
    }
}
