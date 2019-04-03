package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;

class InviteServiceImpl implements InviteService {

    private InviteRepository inviteRepository;
    private ApartmentService apartmentService;

    InviteServiceImpl (InviteRepository inviteRepository, ApartmentService apartmentService) {
        this.inviteRepository = inviteRepository;
        this.apartmentService = apartmentService;
    }

    @Override
    public void createInvite(String from, String to, String apartment) {
        if (!inviteRepository.existsByReceiverAndApartmentId(to, apartment)) {
            inviteRepository.save(Invite.builder()
                    .sender(from)
                    .receiver(to)
                    .apartmentId(apartment)
                    .build());
        }
    }

    @Override
    public boolean isAcceptedInvite(String to, String apartment, boolean isAccepted) {
        Invite existingInvite = inviteRepository.findByReceiverAndApartmentId(to, apartment);
        inviteRepository.deleteByReceiverAndApartmentId(existingInvite.getReceiver(), existingInvite.getApartmentId());
        if (isAccepted) {
            apartmentService.addTenant(to, apartment);
            return true;
        }
        return false;
    }
}
