package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.dto.User;
import com.piekoszek.nowaksharedrent.dto.UserApartment;
import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.time.TimeService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import java.util.Calendar;

@AllArgsConstructor
class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;
    private UserService userService;
    private UuidService uuidService;
    private TimeService timeService;

    @Override
    public void createApartment(String address, String city, String adminEmail) {
        String apartmentId = uuidService.generateUuid();
        Apartment apartmentToCreate = new Apartment(apartmentId, address, city, adminEmail);
        apartmentRepository.save(apartmentToCreate);
        updateRent(apartmentId, Rent.builder().value(0).build());
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
    public void updateRent(String apartmentId, Rent newRent) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        Calendar currDate = timeService.currentDateAndTime();

        //uncomment block below if you wish new rent value to be used from the next month instead of current
        /*if (currDate.get(Calendar.MONTH) == Calendar.DECEMBER) {
            currDate.set(Calendar.MONTH, 0);
            currDate.set(Calendar.YEAR, currDate.get(Calendar.YEAR)+1);
        } else {
            currDate.set(Calendar.MONTH, currDate.get(Calendar.MONTH)+1);
        }*/

        currDate.set(Calendar.DAY_OF_MONTH, 1);
        currDate.set(Calendar.HOUR_OF_DAY, 0);
        currDate.set(Calendar.MINUTE, 0);
        currDate.set(Calendar.SECOND, 0);
        currDate.set(Calendar.MILLISECOND, 1);
        apartment.updateRent(Rent.builder()
                .borderDate(currDate.getTimeInMillis())
                .value(newRent.getValue())
                .build());
        apartmentRepository.save(apartment);
    }
}
