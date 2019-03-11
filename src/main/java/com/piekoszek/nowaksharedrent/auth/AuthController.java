package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class AuthController {

    private AuthService authService;
    private JwtService jwtService;

    AuthController (AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/signup")
    ResponseEntity<Object> createAccount(@RequestBody Account account) {
        if(authService.createAccount(account)) {
            JwtData jwtData = JwtData.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .build();
            String token = jwtService.generateToken(jwtData);
            return new ResponseEntity<>(new MessageResponse(token), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody Account input) {
        Account account = authService.findAccount (input.getEmail());
        if (account != null && input.getPassword().equals(account.getPassword())) {
            JwtData jwtData = JwtData.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .build();
            String token = jwtService.generateToken(jwtData);
            return new ResponseEntity<>(new MessageResponse(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }
}
