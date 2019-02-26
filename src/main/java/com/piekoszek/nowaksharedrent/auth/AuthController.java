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
    ResponseEntity<Object> loginAccount(@RequestBody Account inputAccount) {
        Account account = authService.findAccount (inputAccount.getEmail());
        if (account != null && inputAccount.getPassword().equals(account.getPassword())) {
            return new ResponseEntity<>(new MessageResponse("Login successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }
}
