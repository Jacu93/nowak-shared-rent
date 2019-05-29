package com.piekoszek.nowaksharedrent.config;

import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
class UndefinedExceptionHandlerController {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowableException (Throwable ex, WebRequest request) {
        return new ResponseEntity<>(new MessageResponse("Undefined error! Sorry, we are working on that"), HttpStatus.BAD_REQUEST);
    }
}
