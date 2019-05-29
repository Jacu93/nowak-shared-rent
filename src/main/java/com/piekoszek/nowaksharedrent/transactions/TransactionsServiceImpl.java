package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.dto.UserService;
import com.piekoszek.nowaksharedrent.transactions.exceptions.TransactionCreatorException;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.Set;

@AllArgsConstructor
class TransactionsServiceImpl implements TransactionsService {

    TransactionsRepository transactionsRepository;
    ApartmentService apartmentService;
    UserService userService;
    UuidService uuidService;

    @Override
    public void newTransaction(Transaction transaction, String email) {

        Validator validator;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate (transaction);

        if (!userService.isAccountExists(email)) {
            throw new TransactionCreatorException("User with email " + email + " not found!");
        }
        if (apartmentService.getApartment(transaction.getApartmentId()) == null) {
            throw new TransactionCreatorException("Apartment doesn't exist!");
        }
        if (!TransactionType.contains(transaction.getType().toString())) {
            throw new TransactionCreatorException("Incorrect transaction type!");
        }
        if (constraintViolations.size()>0) {
            throw new TransactionCreatorException(constraintViolations.iterator().next().getMessage());
        }

        Calendar currDate = Calendar.getInstance();
        String monthlyPaymentsId = (currDate.get(Calendar.MONTH)+1) + "_" + currDate.get(Calendar.YEAR) + "_" + transaction.getApartmentId();

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
                .paidBy((transaction.getType().toString().equals("BILL")) ? null : email)
                .value(transaction.getValue())
                .build();

        currTransactions.newTransaction(newTransaction);
        transactionsRepository.save(currTransactions);


        if (transaction.getType().name().equals("BILL")) {
            apartmentService.updateBalance(transaction.getApartmentId(), transaction.getValue());
        } else {
            apartmentService.updateBalance(email, transaction.getApartmentId(), transaction.getValue());

        }
    }

    @Override
    public Transactions getTransactionsFromMonth(String transactionsId) {

        return transactionsRepository.findById(transactionsId);
    }
}
