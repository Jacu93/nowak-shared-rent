package com.piekoszek.nowaksharedrent.transactions;

import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class Transactions {

    @Id
    private String id;
    private Set<Transaction> transactions;

    Transactions(String id) {
        this.id = id;
        this.transactions = new HashSet<>();
    }

    void newTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
