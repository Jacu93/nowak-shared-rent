package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    private TransactionsService transactionsService;
    private JwtService jwtService;

    TransactionsController(TransactionsService transactionsService, JwtService jwtService) {
        this.transactionsService = transactionsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/transaction")
    ResponseEntity<Object> newTransaction(@RequestBody Transaction transaction, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        transactionsService.newTransaction(transaction, jwtData.getEmail());
        return new ResponseEntity<>(new MessageResponse("Transaction added."), HttpStatus.CREATED);
    }
}
