package com.piekoszek.nowaksharedrent.apartment;

import java.util.List;

public interface ApartmentService {

    void createApartment(Apartment apartment);
    void getApartmentDetails(String id);
    List<Apartment> getApartments (String email);
}
