package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.user.UserService;
import com.piekoszek.nowaksharedrent.time.TimeService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransactionsServiceConfiguration {

    TransactionsService transactionsService (ApartmentService apartmentService, UserService userService, UuidService uuidService, TimeService timeService) {
        return new TransactionsServiceImpl(new InMemoryTransactionRepository(), apartmentService, userService, uuidService, timeService);
    }

    @Bean
    TransactionsService transactionsService (TransactionsRepository transactionsRepository, ApartmentService apartmentService, UserService userService, UuidService uuidService, TimeService timeService) {
        return new TransactionsServiceImpl (transactionsRepository, apartmentService, userService, uuidService, timeService);
    }
}
