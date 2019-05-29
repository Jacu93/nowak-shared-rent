package com.piekoszek.nowaksharedrent.invite;

import org.springframework.data.repository.Repository;

import java.util.List;

interface InvitationRepository extends Repository<Invitation, String> {

    boolean existsByReceiverAndApartmentId(String to, String apartment);
    void save(Invitation invitation);
    Invitation findById(String id);
    void deleteById(String id);
    List<Invitation> findAllByReceiver(String to);
}
