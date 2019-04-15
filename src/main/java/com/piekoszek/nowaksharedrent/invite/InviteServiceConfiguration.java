package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.dto.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InviteServiceConfiguration {

    @Bean
    InviteService inviteService (InvitationRepository invitationRepository, ApartmentService apartmentService, UserRepository userRepository) {
        return new InviteServiceImpl(invitationRepository, apartmentService, userRepository);
    }
}
