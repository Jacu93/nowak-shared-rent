package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.Apartment;
import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.apartment.Tenant;
import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.time.TimeService;
import com.piekoszek.nowaksharedrent.transactions.exceptions.TransactionCreatorException;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class TransactionsServiceImpl implements TransactionsService {

    private TransactionsRepository transactionsRepository;
    private ApartmentService apartmentService;
    private UserService userService;
    private UuidService uuidService;
    private TimeService timeService;

    @Override
    public void newTransaction(Transaction transaction, String email) {

        Apartment apartment = apartmentService.getApartment(transaction.getApartmentId());

        if (!userService.isAccountExists(email)) {
            throw new TransactionCreatorException("User with email " + email + " not found!");
        }
        if (apartment == null) {
            throw new TransactionCreatorException("Apartment doesn't exist!");
        }
        if (transaction.getType() == TransactionType.BILL && !apartment.getAdmin().equals(email)) {
            throw new TransactionCreatorException("Bill transaction type is available only for admin of an apartment!");
        }

        Calendar currDate = Calendar.getInstance();
        currDate.setTimeInMillis(timeService.millisSinceEpoch());
        String monthlyPaymentsId = (currDate.get(Calendar.MONTH)+1) + "_" + currDate.get(Calendar.YEAR) + "_" + transaction.getApartmentId();

        Transactions currTransactions = transactionsRepository.findById(monthlyPaymentsId);
        if (currTransactions == null) {
            Set<Payer> balanceLog = new HashSet<>();
            for (Tenant tenant : apartment.getTenants()) {
                balanceLog.add(new Payer(tenant.getEmail(), tenant.getName(), 0));
            }
            currTransactions = new Transactions(monthlyPaymentsId, balanceLog);
        }

        Transaction newTransaction = Transaction.builder()
                .createdAt(currDate.getTimeInMillis())
                .id(uuidService.generateUuid())
                .apartmentId(transaction.getApartmentId())
                .title(transaction.getTitle())
                .type(transaction.getType())
                .paidBy((transaction.getType() == TransactionType.BILL) ? null : email)
                .value(transaction.getValue())
                .build();

        currTransactions.newTransaction(newTransaction);
        transactionsRepository.save(currTransactions);


        if (transaction.getType() == TransactionType.BILL) {
            Transactions transactions = transactionsRepository.findById(monthlyPaymentsId);
            transactions.updateBalance(transaction.getValue());
            transactionsRepository.save(transactions);
        } else {
            Transactions transactions = transactionsRepository.findById(monthlyPaymentsId);
            transactions.updateBalance(email, transaction.getValue());
            transactionsRepository.save(transactions);
        }
    }

    @Override
    public Transactions getTransactionsFromMonth(int month, int year, String apartmentId) {

        String monthlyPaymentsId = month + "_" + year + "_" + apartmentId;
        return transactionsRepository.findById(monthlyPaymentsId);
    }
}
