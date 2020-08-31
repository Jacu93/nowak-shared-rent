package com.piekoszek.nowaksharedrent.transactions;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class Transactions {

    @Id
    private String id;
    private List<Transaction> transactions;
    private Set<Payer> payers;

    Transactions(String id, Set<Payer> payers) {
        this.id = id;
        this.transactions = new ArrayList<>();
        this.payers = payers;
    }

    void updateBalance(String payerEmail, int transactionValue) {
        for (Payer payer : this.payers) {
            if (payer.getEmail().equals(payerEmail)) {
                payer.updateBalance(transactionValue / payers.size() - transactionValue);
            } else {
                payer.updateBalance(Math.round(transactionValue / payers.size()));
            }
        }
    }

    void updateBalance(int transactionValue) {
        for (Payer payer : this.payers) {
            payer.updateBalance(transactionValue / payers.size());
        }
    }

    void newTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
