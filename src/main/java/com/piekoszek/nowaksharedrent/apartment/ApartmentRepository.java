package com.piekoszek.nowaksharedrent.apartment;

import org.springframework.data.repository.Repository;

interface ApartmentRepository extends Repository<Apartment, String> {

    void save(Apartment apartment);
    Apartment findById(String id);
}
