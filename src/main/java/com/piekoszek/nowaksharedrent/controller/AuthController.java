package com.piekoszek.nowaksharedrent.controller;

import com.piekoszek.nowaksharedrent.model.Account;
import com.piekoszek.nowaksharedrent.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {
    private AuthService authService;

    public AuthController (AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        if(authService.createAccount(account))
            return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
        return  new ResponseEntity<>("Account with such email already exists", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/account")
    public  ResponseEntity<Object> getAccount() {
        return new ResponseEntity<>(authService.getAccount(), HttpStatus.OK);
    }
}
