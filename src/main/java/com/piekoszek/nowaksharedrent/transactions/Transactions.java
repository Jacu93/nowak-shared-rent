package com.piekoszek.nowaksharedrent.transactions;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Transactions {

    @Id
    private String id;
    private List<Transaction> transactions;

    Transactions(String id) {
        this.id = id;
        this.transactions = new ArrayList<>();
    }

    void newTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
