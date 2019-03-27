package com.piekoszek.nowaksharedrent.apartment;

import java.util.List;

public interface ApartmentService {

    void createApartment(Apartment apartment);
    Apartment getApartmentDetails(String id);
    List<Apartment> getApartments(String email);
    boolean addTenant(String email, String apartmentId);
}
