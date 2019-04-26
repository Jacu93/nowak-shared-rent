package com.piekoszek.nowaksharedrent.apartment;

public interface ApartmentService {

    void createApartment(String address, String city, String adminEmail);
    Apartment getApartment(String id);
    void addTenant(String email, String apartmentId);
}