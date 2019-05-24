package com.piekoszek.nowaksharedrent.payment;

import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class MonthlyPayments {

    @Id
    private String id;
    private Set<Payment> payments;

    MonthlyPayments (String id) {
        this.id = id;
        this.payments = new HashSet<>();
    }

    void addPayment (Payment payment) {
        payments.add(payment);
    }
}
