package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.apartment.Tenant;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class TransactionsServiceImpl implements TransactionsService {

    TransactionsRepository transactionsRepository;
    ApartmentService apartmentService;
    UuidService uuidService;

    @Override
    public void addPayment(Transaction transaction, String email) {

        Calendar currDate = Calendar.getInstance();
        String monthlyPaymentsId = transaction.getApartmentId() + "m" + (currDate.get(Calendar.MONTH)+1) + "y" + currDate.get(Calendar.YEAR);

        Transactions currTransactions = transactionsRepository.findById(monthlyPaymentsId);
        if (currTransactions == null) {
            currTransactions = new Transactions(monthlyPaymentsId);
        }

        Transaction newTransaction = Transaction.builder()
                .createdAt(currDate.getTimeInMillis())
                .id(uuidService.generateUuid())
                .apartmentId(transaction.getApartmentId())
                .title(transaction.getTitle())
                .type(transaction.getType())
                .paidBy(email)
                .value(transaction.getValue())
                .build();

        currTransactions.addPayment(newTransaction);
        transactionsRepository.save(currTransactions);

        Set<Tenant> tenants = apartmentService.getApartment(transaction.getApartmentId()).getTenants();
        int amountToPay = transaction.getValue()/tenants.size();

        if (transaction.getType().equals("bill")) {
            apartmentService.updateBalance(new HashSet<>(), transaction.getApartmentId(), amountToPay);
        } else {
            Set<String> excluded = new HashSet<>();
            excluded.add(email);
            apartmentService.updateBalance(excluded, transaction.getApartmentId(), amountToPay);
            excluded.clear();
            for (Tenant tenant : tenants) {
                if (!tenant.getEmail().equals(email))
                excluded.add(tenant.getEmail());
            }
            apartmentService.updateBalance(excluded, transaction.getApartmentId(), -transaction.getValue()+amountToPay);
        }
    }
}
