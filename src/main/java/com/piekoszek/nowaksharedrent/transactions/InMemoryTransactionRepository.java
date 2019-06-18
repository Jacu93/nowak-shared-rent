package com.piekoszek.nowaksharedrent.transactions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryTransactionRepository implements TransactionsRepository {

    private Map<String, Transactions> map = new ConcurrentHashMap<>();

    @Override
    public Transactions findById(String id) {
        return map.get(id);
    }

    public void save(Transactions transactions) {
        map.put(transactions.getId(), transactions);
    }
}
