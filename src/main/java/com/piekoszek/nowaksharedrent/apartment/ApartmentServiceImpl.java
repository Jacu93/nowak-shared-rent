package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.auth.AccountRepository;

import java.util.List;
import java.util.UUID;

class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;
    private AccountRepository accountRepository;

    ApartmentServiceImpl(ApartmentRepository apartmentRepository, AccountRepository accountRepository) {
        this.apartmentRepository = apartmentRepository;
        this.accountRepository = accountRepository;
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
        if(accountRepository.existsByEmail(email) && !apartment.hasTenant(email)) {
            apartment.addTenant(Tenant.builder()
                    .email(email)
                    .name(accountRepository.findByEmail(email).getName())
                    .build());
            apartmentRepository.save(apartment);
            return true;
        }
        return false;
    }
}
