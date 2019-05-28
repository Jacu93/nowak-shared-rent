package com.piekoszek.nowaksharedrent.transactions;

public enum TransactionType {
    BILL, COMMON_PRODUCT;

    static boolean contains(String value) {

        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
