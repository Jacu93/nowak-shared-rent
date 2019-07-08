package com.piekoszek.nowaksharedrent.apartment;

public interface ApartmentService {

    void createApartment(String address, String city, String adminEmail);
    Apartment getApartment(String id);
    void addTenant(String email, String apartmentId);
    boolean hasTenant(Apartment apartment, String email);
    void updateBalance(String payerEmail, String apartmentId, int value);
    void updateBalance(String apartmentId, int value);
    void updateRent(String apartmentId, Rent newRent);
}