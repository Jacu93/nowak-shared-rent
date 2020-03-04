package com.piekoszek.nowaksharedrent.transactions;

import com.piekoszek.nowaksharedrent.jwt.Jwt;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
class TransactionsController {

    private TransactionsService transactionsService;

    @PostMapping("/transaction")
    ResponseEntity<Object> newTransaction(@RequestBody @Valid Transaction transaction, @Jwt JwtData jwtData) {
        transactionsService.newTransaction(transaction, jwtData.getEmail());
        return new ResponseEntity<>(new MessageResponse("Transaction added."), HttpStatus.CREATED);
    }

    @GetMapping("/transactions/{month}/{year}/{apartmentId}")
    ResponseEntity<Object> getTransactionsFromMonth(@PathVariable("month") int month, @PathVariable("year") int year, @PathVariable("apartmentId") String apartmentId, @Jwt JwtData jwtData) {
        Transactions transactions = transactionsService.getTransactionsFromMonth(month, year, apartmentId);
        if (transactions == null) {
            return new ResponseEntity<>(new MessageResponse("No transactions with given id found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/types")
    ResponseEntity<Object> getTransactionTypes(@Jwt JwtData jwtData) {
        return new ResponseEntity<>(TransactionType.getTransactionTypesAsList(), HttpStatus.OK);
    }

    @GetMapping("/transactions/balance/{apartmentId}")
    ResponseEntity<Object> getLastTwoMonthsBalance(@PathVariable("apartmentId") String apartmentId, @Jwt JwtData jwtData) {
        return new ResponseEntity<>(transactionsService.getLastTwoMonthsBalance(apartmentId), HttpStatus.OK);
    }
}
