package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.apartment.Tenant;
import lombok.Getter;

@Getter
public class Payer extends Tenant {

    private int balance;

    Payer(String email, String name, int balance) {
        super(email, name);
        this.balance = balance;
    }

    void updateBalance(int value) {
        this.balance += value;
    }
}
