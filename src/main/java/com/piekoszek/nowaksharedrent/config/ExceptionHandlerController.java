package com.piekoszek.nowaksharedrent.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.piekoszek.nowaksharedrent.auth.exceptions.ResetPasswordException;
import com.piekoszek.nowaksharedrent.invite.exceptions.InviteCreatorException;
import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import com.piekoszek.nowaksharedrent.transactions.TransactionType;
import com.piekoszek.nowaksharedrent.transactions.exceptions.TransactionCreatorException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionHandlerController {

    @ExceptionHandler(InviteCreatorException.class)
    public ResponseEntity<Object> handleInviteCreatorException (InviteCreatorException ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Invite unsuccessful: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException (InvalidTokenException ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Invalid token: " + ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TransactionCreatorException.class)
    public ResponseEntity<Object> handleTransactionCreatorException (TransactionCreatorException ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Transaction could not be added: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInviteCreatorException (InvalidFormatException ex, WebRequest request) {
        if (ex.getTargetType().isEnum()) {
            return new ResponseEntity<>(new MessageResponse(ex.getValue() + " is not value of: " + ex.getTargetType().getSimpleName() + " type. Available types: " + TransactionType.getTransactionTypesAsString()), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ResetPasswordException.class)
    public ResponseEntity<Object> handleResetPasswordException (ResetPasswordException ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Password change unsuccessful: " + ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}
