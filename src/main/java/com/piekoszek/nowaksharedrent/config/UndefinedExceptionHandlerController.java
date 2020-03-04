package com.piekoszek.nowaksharedrent.config;

import com.piekoszek.nowaksharedrent.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
class UndefinedExceptionHandlerController {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowableException (Throwable ex, WebRequest request) {
        log.error("Undefined exception: ",ex);
        return new ResponseEntity<>(new MessageResponse("Undefined error! Sorry, we are working on that"), HttpStatus.BAD_REQUEST);
    }
}
