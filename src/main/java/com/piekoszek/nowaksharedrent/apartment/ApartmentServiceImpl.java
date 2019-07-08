package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.User;
import com.piekoszek.nowaksharedrent.dto.UserApartment;
import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import java.util.Calendar;

@AllArgsConstructor
class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;
    private UserService userService;
    private UuidService uuidService;

    @Override
    public void createApartment(String address, String city, String adminEmail) {
        Apartment apartmentToCreate = new Apartment(uuidService.generateUuid(), address, city, adminEmail);
        apartmentRepository.save(apartmentToCreate);
        addTenant(adminEmail, apartmentToCreate.getId());
    }

    @Override
    public Apartment getApartment(String id) {
        return apartmentRepository.findById(id);
    }

    @Override
    public void addTenant(String email, String apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        User user = userService.getUser(email);

        if(user != null && !apartment.hasTenant(email)) {
            apartment.addTenant(Tenant.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .balance(0)
                    .build());
            apartmentRepository.save(apartment);

            userService.addApartmentToUser(UserApartment.builder()
                    .id(apartment.getId())
                    .name(apartment.getAddress() + ", " + apartment.getCity())
                    .isOwner(apartment.getAdmin().equals(email))
                    .build(), user);
            userService.createUser(user);
        }
    }

    @Override
    public boolean hasTenant(Apartment apartment, String email) {
        return apartment.hasTenant(email);
    }

    @Override
    public void updateBalance(String payerEmail, String apartmentId, int value) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        apartment.updateBalance(payerEmail, value);
        apartmentRepository.save(apartment);
    }

    @Override
    public void updateBalance(String apartmentId, int value) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        apartment.updateBalance(value);
        apartmentRepository.save(apartment);
    }

    @Override
    public void updateRent(String apartmentId, Rent newRent) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        Calendar currDate = Calendar.getInstance();
        currDate.set(Calendar.MONTH, currDate.get(Calendar.MONTH)+1);
        currDate.set(Calendar.DAY_OF_MONTH, 1);
        currDate.set(Calendar.HOUR_OF_DAY, 0);
        currDate.set(Calendar.MINUTE, 0);
        currDate.set(Calendar.SECOND, 0);
        currDate.set(Calendar.MILLISECOND, 0);
        apartment.updateRent(Rent.builder()
                .borderDate(currDate.getTimeInMillis())
                .value(newRent.getValue())
                .build());
        apartmentRepository.save(apartment);
    }
}
