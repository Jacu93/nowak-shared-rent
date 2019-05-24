package com.piekoszek.nowaksharedrent.payment;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.apartment.Tenant;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class MonthlyPaymentsServiceImpl implements MonthlyPaymentsService {

    MonthlyPaymentsRepository monthlyPaymentsRepository;
    ApartmentService apartmentService;
    UuidService uuidService;

    @Override
    public void addPayment(Payment payment, String email) {

        Calendar currDate = Calendar.getInstance();
        String monthlyPaymentsId = payment.getApartmentId() + currDate.get(Calendar.MONTH) + currDate.get(Calendar.YEAR);

        MonthlyPayments currMonthlyPayments = monthlyPaymentsRepository.findById(monthlyPaymentsId);
        if (currMonthlyPayments == null) {
            currMonthlyPayments = new MonthlyPayments(monthlyPaymentsId);
        }

        Payment newPayment = Payment.builder()
                .createdAt(currDate.getTimeInMillis())
                .id(uuidService.generateUuid())
                .apartmentId(payment.getApartmentId())
                .title(payment.getTitle())
                .type(payment.getType())
                .paidBy(email)
                .value(payment.getValue())
                .build();

        currMonthlyPayments.addPayment(newPayment);
        monthlyPaymentsRepository.save(currMonthlyPayments);

        Set<Tenant> tenants = apartmentService.getApartment(payment.getApartmentId()).getTenants();
        int amountToPay = payment.getValue()/tenants.size();

        if (payment.getType().equals("bill")) {
            apartmentService.updateBalance(new HashSet<>(), payment.getApartmentId(), amountToPay);
        } else {
            Set<String> excluded = new HashSet<>();
            excluded.add(email);
            apartmentService.updateBalance(excluded, payment.getApartmentId(), amountToPay);
            excluded.clear();
            for (Tenant tenant : tenants) {
                if (!tenant.getEmail().equals(email))
                excluded.add(tenant.getEmail());
            }
            apartmentService.updateBalance(excluded, payment.getApartmentId(), -payment.getValue()+amountToPay);
        }
    }
}
