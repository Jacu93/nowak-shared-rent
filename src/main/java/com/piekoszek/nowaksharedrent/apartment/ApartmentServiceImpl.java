package com.piekoszek.nowaksharedrent.apartment;

import java.util.List;
import java.util.UUID;

class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;

    ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public void createApartment(Apartment apartment) {
        Apartment apartmentToCreate = new Apartment(UUID.randomUUID().toString(), apartment.getAddress(), apartment.getCity(), apartment.getAdmin());
        apartmentRepository.save(apartmentToCreate);
    }

    @Override
    public void getApartmentDetails(String id) {

    }

    @Override
    public List<Apartment> getApartments (String email) {
        return apartmentRepository.findAllByAdmin(email);
    }
}
