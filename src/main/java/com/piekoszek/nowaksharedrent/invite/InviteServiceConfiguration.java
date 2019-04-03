package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InviteServiceConfiguration {

    @Bean
    InviteService inviteService (InviteRepository inviteRepository, ApartmentService apartmentService) {
        return new InviteServiceImpl(inviteRepository, apartmentService);
    }
}
