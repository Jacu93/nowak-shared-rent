package com.piekoszek.nowaksharedrent.transactions.exceptions;

public class TransactionCreatorException extends RuntimeException {

    public TransactionCreatorException() {
        super("Transaction couldn't be added");
    }

    public TransactionCreatorException(String message) {
        super(message);
    }
}
