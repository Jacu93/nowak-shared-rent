package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.Apartment;
import com.piekoszek.nowaksharedrent.apartment.ApartmentService;

import java.util.ArrayList;

class InviteServiceImpl implements InviteService {

    private InviteRepository inviteRepository;
    private ApartmentService apartmentService;

    InviteServiceImpl (InviteRepository inviteRepository, ApartmentService apartmentService) {
        this.inviteRepository = inviteRepository;
        this.apartmentService = apartmentService;
    }

    @Override
    public void createInvite(String from, String to, String apartmentId) {
        if (!inviteRepository.existsByReceiverAndApartmentId(to, apartmentId) && apartmentService.getApartment(apartmentId).getAdmin().equals(from)) {
            Apartment apartment = apartmentService.getApartment(apartmentId);
            inviteRepository.save(Invite.builder()
                    .sender(from)
                    .receiver(to)
                    .apartmentId(apartmentId)
                    .apartmentName(apartment.getAddress() + ", " + apartment.getCity())
                    .build());
        }
    }

    @Override
    public void resolveInvite(String to, String apartment, boolean isAccepted) {
        Invite existingInvite = inviteRepository.findByReceiverAndApartmentId(to, apartment);
        inviteRepository.deleteByReceiverAndApartmentId(existingInvite.getReceiver(), existingInvite.getApartmentId());
        if (isAccepted) {
            apartmentService.addTenant(to, apartment);
        }
    }

    @Override
    public ArrayList<Invite> getInvites (String to) {
        return new ArrayList<>(inviteRepository.findAllByReceiver(to));
    }
}
