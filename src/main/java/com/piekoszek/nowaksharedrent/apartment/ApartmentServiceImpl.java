package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.UserRepository;

import java.util.List;
import java.util.UUID;

class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;
    private UserRepository userRepository;

    ApartmentServiceImpl(ApartmentRepository apartmentRepository, UserRepository userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createApartment(Apartment apartment) {
        Apartment apartmentToCreate = new Apartment(UUID.randomUUID().toString(), apartment.getAddress(), apartment.getCity(), apartment.getAdmin());
        apartmentRepository.save(apartmentToCreate);
    }

    @Override
    public Apartment getApartmentDetails(String id) {
        return apartmentRepository.findById(id);
    }

    @Override
    public List<Apartment> getApartments(String email) {
        return apartmentRepository.findAllByAdmin(email);
    }

    @Override
    public boolean addTenant(String email, String apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        if(userRepository.existsByEmail(email) && !apartment.hasTenant(email)) {
            apartment.addTenant(Tenant.builder()
                    .email(email)
                    .name(userRepository.findByEmail(email).getName())
                    .build());
            apartmentRepository.save(apartment);
            return true;
        }
        return false;
    }
}
