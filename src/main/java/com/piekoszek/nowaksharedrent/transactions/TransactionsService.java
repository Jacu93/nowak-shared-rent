package com.piekoszek.nowaksharedrent.transactions;

public interface TransactionsService {

    void newTransaction(Transaction transaction, String email);
    Transactions getTransactionsFromMonth(int month, int year, String apartmentId);
}
