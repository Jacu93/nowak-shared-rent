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

    public static StringBuilder getTransactionTypes() {

        StringBuilder types = new StringBuilder();
        for (TransactionType transactionType : TransactionType.values()) {
            types.append(transactionType.name());
            types.append(", ");
        }
        types.setLength(types.length() - 2);
        return types;
    }
}
