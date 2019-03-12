package com.piekoszek.nowaksharedrent.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
class AuthController {

    private AuthService authService;

    AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    ResponseEntity<Object> createAccount(@RequestBody Account input) {
        if(authService.createAccount(input)) {
            Optional<String> token = authService.loginUser(input);
            if (token.isPresent()) {
                return new ResponseEntity<>(new MessageResponse(token.get()), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new MessageResponse("Unable to generate token"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody Account input) {
        Optional<String> token = authService.loginUser(input);
        if (token.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }
}