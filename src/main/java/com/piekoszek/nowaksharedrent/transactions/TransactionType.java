package com.piekoszek.nowaksharedrent.transactions;

import java.util.ArrayList;
import java.util.List;

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

    public static StringBuilder getTransactionTypesAsString() {

        StringBuilder types = new StringBuilder();
        for (TransactionType transactionType : TransactionType.values()) {
            types.append(transactionType.name());
            types.append(", ");
        }
        types.setLength(types.length() - 2);
        return types;
    }

    public static List<String> getTransactionTypesAsList() {

        List<String> types = new ArrayList<>();
        for (TransactionType transactionType : TransactionType.values()) {
            types.add(transactionType.name());
        }
        return types;
    }
}
