package com.piekoszek.nowaksharedrent.apartment;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface ApartmentRepository extends Repository<Apartment, String> {

    void save(Apartment apartment);
    List<Apartment> findAllByAdmin(String email);
    Apartment findById(String id);
}
