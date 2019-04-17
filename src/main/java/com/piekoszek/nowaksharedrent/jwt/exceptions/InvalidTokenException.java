package com.piekoszek.nowaksharedrent.jwt.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Provided token is invalid");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}