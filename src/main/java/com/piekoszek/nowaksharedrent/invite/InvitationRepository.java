package com.piekoszek.nowaksharedrent.invite;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface InvitationRepository extends Repository<Invitation, String> {

    boolean existsByReceiverAndApartmentId(String to, String apartment);
    void save(Invitation invitation);
    Invitation findByReceiverAndApartmentId(String to, String apartment);
    void deleteByReceiverAndApartmentId(String to, String apartment);
    List<Invitation> findAllByReceiver(String to);
}
