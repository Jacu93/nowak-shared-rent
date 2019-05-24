package com.piekoszek.nowaksharedrent.apartment;

import java.util.Set;

public interface ApartmentService {

    void createApartment(String address, String city, String adminEmail);
    Apartment getApartment(String id);
    void addTenant(String email, String apartmentId);
    boolean hasTenant(Apartment apartment, String email);
    void updateBalance(Set<String> excluded, String apartmentId, int value);
}