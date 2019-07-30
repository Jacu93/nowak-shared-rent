package com.piekoszek.nowaksharedrent.transactions;

import java.util.Map;
import java.util.Set;

public interface TransactionsService {

    void newTransaction(Transaction transaction, String email);
    Transactions getTransactionsFromMonth(int month, int year, String apartmentId);
    Map<Integer, Set<Payer>> getLastMonthsBalance(String apartmentId);
}
