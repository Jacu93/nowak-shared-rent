package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.Apartment;
import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.dto.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class InviteServiceImpl implements InviteService {

    private InvitationRepository invitationRepository;
    private ApartmentService apartmentService;
    private UserRepository userRepository;

    InviteServiceImpl (InvitationRepository invitationRepository, ApartmentService apartmentService, UserRepository userRepository) {
        this.invitationRepository = invitationRepository;
        this.apartmentService = apartmentService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<String> createInvitation(String from, String to, String apartmentId) {
        if (!invitationRepository.existsByReceiverAndApartmentId(to, apartmentId) && apartmentService.getApartment(apartmentId).getAdmin().equals(from) && userRepository.existsByEmail(to)) {
            Apartment apartment = apartmentService.getApartment(apartmentId);
            invitationRepository.save(Invitation.builder()
                    .sender(from)
                    .receiver(to)
                    .apartmentId(apartmentId)
                    .apartmentName(apartment.getAddress() + ", " + apartment.getCity())
                    .build());
            return Optional.of("Invitation created");
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> resolveInvitation(String to, String apartment, boolean isAccepted) {
        Invitation existingInvitation = invitationRepository.findByReceiverAndApartmentId(to, apartment);
        if (existingInvitation != null) {
            invitationRepository.deleteByReceiverAndApartmentId(existingInvitation.getReceiver(), existingInvitation.getApartmentId());
            if (isAccepted) {
                apartmentService.addTenant(to, apartment);
                return Optional.of("Invitation accepted");
            }
            return Optional.of("Invitation rejected");
        }
        return Optional.empty();
    }

    @Override
    public List<Invitation> getInvitations(String to) {
        return new ArrayList<>(invitationRepository.findAllByReceiver(to));
    }
}
