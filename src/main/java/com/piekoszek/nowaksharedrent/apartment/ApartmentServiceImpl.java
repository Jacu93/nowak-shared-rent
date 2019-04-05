package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.User;
import com.piekoszek.nowaksharedrent.dto.UserApartment;
import com.piekoszek.nowaksharedrent.dto.UserRepository;

import java.util.UUID;

class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;
    private UserRepository userRepository;

    ApartmentServiceImpl(ApartmentRepository apartmentRepository, UserRepository userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createApartment(String address, String city, String admin) {
        Apartment apartmentToCreate = new Apartment(UUID.randomUUID().toString(), address, city, admin);
        apartmentRepository.save(apartmentToCreate);
        addTenant(admin, apartmentToCreate.getId());
    }

    @Override
    public Apartment getApartment(String id) {
        return apartmentRepository.findById(id);
    }

    @Override
    public void addTenant(String email, String apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        User user = userRepository.findByEmail(email);

        if(user != null && !apartment.hasTenant(email)) {
            apartment.addTenant(Tenant.builder()
                    .email(email)
                    .name(userRepository.findByEmail(email).getName())
                    .build());
            apartmentRepository.save(apartment);

            user.addApartment(UserApartment.builder()
                    .id(apartment.getId())
                    .name(apartment.getAddress() + ", " + apartment.getCity())
                    .isOwner(apartment.getAdmin().equals(email))
                    .build());
            userRepository.save(user);
        }
    }
}
