package com.piekoszek.nowaksharedrent.invite.exceptions;

public class InviteCreatorException extends RuntimeException {

    public InviteCreatorException() {
        super("Invitation couldn't be created.");
    }

    public InviteCreatorException(String message) {
        super(message);
    }
}