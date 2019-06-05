package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.Apartment;
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

    private TransactionsRepository transactionsRepository;
    private ApartmentService apartmentService;
    private UserService userService;
    private UuidService uuidService;

    @Override
    public void newTransaction(Transaction transaction, String email) {

        Validator validator;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate (transaction);
        Apartment apartment = apartmentService.getApartment(transaction.getApartmentId());

        if (!userService.isAccountExists(email)) {
            throw new TransactionCreatorException("User with email " + email + " not found!");
        }
        if (apartment == null) {
            throw new TransactionCreatorException("Apartment doesn't exist!");
        }
        if (constraintViolations.size()>0) {
            throw new TransactionCreatorException(constraintViolations.iterator().next().getMessage());
        }
        if (transaction.getType() == TransactionType.BILL && !apartment.getAdmin().equals(email)) {
            throw new TransactionCreatorException("Bill transaction type is available only for admin of an apartment!");
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
                .paidBy((transaction.getType() == TransactionType.BILL) ? null : email)
                .value(transaction.getValue())
                .build();

        currTransactions.newTransaction(newTransaction);
        transactionsRepository.save(currTransactions);


        if (transaction.getType() == TransactionType.BILL) {
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
