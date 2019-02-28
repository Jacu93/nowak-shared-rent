package com.piekoszek.nowaksharedrent.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class AuthController {

    private AuthService authService;

    AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    ResponseEntity<Object> createAccount(@RequestBody Account account) {
        if(authService.createAccount(account)) {
            return new ResponseEntity<>(new MessageResponse("Account created successfully"), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody Account input) {
        Account account = authService.findAccount (input.getEmail());
        if (account != null && input.getPassword().equals(account.getPassword())) {
            return new ResponseEntity<>(new MessageResponse("Login successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }
}
