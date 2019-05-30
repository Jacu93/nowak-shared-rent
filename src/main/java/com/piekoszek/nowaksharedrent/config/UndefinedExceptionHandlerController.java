package com.piekoszek.nowaksharedrent.config;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
class UndefinedExceptionHandlerController {

//    @ExceptionHandler(Throwable.class)
//    public ResponseEntity<Object> handleThrowableException (Throwable ex, WebRequest request) {
//        return new ResponseEntity<>(new MessageResponse("Undefined error! Sorry, we are working on that"), HttpStatus.BAD_REQUEST);
//    }
}
