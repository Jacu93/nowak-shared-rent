package com.piekoszek.nowaksharedrent.transactions;

import org.springframework.data.repository.Repository;

public interface TransactionsRepository extends Repository<Transactions, String> {
    void save(Transactions transactions);
    Transactions findById(String id);
}
