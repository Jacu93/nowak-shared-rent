package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionsServiceConfiguration {

    @Bean
    TransactionsService transactionsService (TransactionsRepository transactionsRepository, ApartmentService apartmentService, UuidService uuidService) {
        return new TransactionsServiceImpl (transactionsRepository, apartmentService, uuidService);
    }
}
