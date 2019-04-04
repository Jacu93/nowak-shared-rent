package com.piekoszek.nowaksharedrent.apartment;

public interface ApartmentService {

    void createApartment(String address, String city, String admin);
    Apartment getApartment(String id);
    void addTenant(String email, String apartmentId);
}