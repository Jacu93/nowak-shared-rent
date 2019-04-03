package com.piekoszek.nowaksharedrent.invite;

import org.springframework.data.repository.Repository;

public interface InviteRepository extends Repository<Invite, String> {

    boolean existsByReceiverAndApartmentId(String to, String apartment);
    void save(Invite invite);
    Invite findByReceiverAndApartmentId(String to, String apartment);
    void deleteByReceiverAndApartmentId(String to, String apartment);
}
