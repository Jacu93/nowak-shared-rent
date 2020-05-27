package com.piekoszek.nowaksharedrent.auth.exceptions;

public class ResetPasswordException extends RuntimeException {

    public ResetPasswordException() {
        super("Password change unsuccessful.");
    }

    public ResetPasswordException(String message) {
        super(message);
    }
}