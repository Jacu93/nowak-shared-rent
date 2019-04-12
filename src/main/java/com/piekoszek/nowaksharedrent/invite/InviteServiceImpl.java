package com.piekoszek.nowaksharedrent.invite;

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
    public void createInvite(String from, String to, String apartment) {
        if (!inviteRepository.existsByReceiverAndApartmentId(to, apartment) && apartmentService.getApartment(apartment).getAdmin().equals(from)) {
            inviteRepository.save(Invite.builder()
                    .sender(from)
                    .receiver(to)
                    .apartmentId(apartment)
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
