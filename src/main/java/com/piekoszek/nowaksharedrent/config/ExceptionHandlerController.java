package com.piekoszek.nowaksharedrent.config;

import com.piekoszek.nowaksharedrent.invite.exceptions.InviteCreatorException;
import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(InviteCreatorException.class)
    public ResponseEntity<Object> handleInviteCreatorException (InviteCreatorException ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Invite unsuccessful: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException (InvalidTokenException ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Invalid token: " + ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
