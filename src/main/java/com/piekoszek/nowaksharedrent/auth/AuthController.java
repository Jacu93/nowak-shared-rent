package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
class AuthController {

    private AuthService authService;

    AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
        Optional<String> token = authService.createAccount(account);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody Account account) {
        Optional<String> token = authService.loginUser(account);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/auth/refresh")
    ResponseEntity<Object> refreshToken(@RequestHeader("Authorization") String oldToken) {
        Optional<String> token = authService.refreshToken(oldToken);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Token malformed or expired. Please login again."), HttpStatus.UNAUTHORIZED);
    }
}