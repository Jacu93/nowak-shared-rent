package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InviteServiceConfiguration {

    InviteService inviteService (ApartmentService apartmentService, UserService userService, UuidService uuidService) {
        return new InviteServiceImpl(new InMemoryInvitationRepository(), apartmentService, userService, uuidService);
    }

    @Bean
    InviteService inviteService (InvitationRepository invitationRepository, ApartmentService apartmentService,UserService userService, UuidService uuidService) {
        return new InviteServiceImpl(invitationRepository, apartmentService, userService, uuidService);
    }
}
