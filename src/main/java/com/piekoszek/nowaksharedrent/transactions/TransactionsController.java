package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class TransactionsController {

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

    @GetMapping("/transactions/{id}")
    ResponseEntity<Object> getTransactionsFromMonth(@PathVariable("id") String id) {
        Transactions transactions = transactionsService.getTransactionsFromMonth(id);
        if (transactions == null) {
            return new ResponseEntity<>(new MessageResponse("No transactions with given id found."), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/types")
    ResponseEntity<Object> getTransactionTypes() {
        return new ResponseEntity<>(TransactionType.getTransactionTypesAsList(), HttpStatus.OK);
    }
}
