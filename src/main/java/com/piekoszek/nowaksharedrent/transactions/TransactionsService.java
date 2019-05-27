package com.piekoszek.nowaksharedrent.transactions;

public interface TransactionsService {

    void addPayment (Transaction transaction, String email);
}
