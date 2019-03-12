package com.piekoszek.nowaksharedrent.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthController {

    private AuthService authService;

    AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    ResponseEntity<Object> createAccount(@RequestBody Account input) {
        if(authService.createAccount(input)) {
            String token = authService.loginUser(input);
            return new ResponseEntity<>(new MessageResponse(token), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody Account input) {
        String token = authService.loginUser(input);
        if (!token.equals("")) {
            return new ResponseEntity<>(new MessageResponse(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }
}