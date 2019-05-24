package com.piekoszek.nowaksharedrent.payment;

import org.springframework.data.repository.Repository;

public interface MonthlyPaymentsRepository extends Repository<MonthlyPayments, String> {
    void save(MonthlyPayments monthlyPayments);
    MonthlyPayments findById(String id);
}
