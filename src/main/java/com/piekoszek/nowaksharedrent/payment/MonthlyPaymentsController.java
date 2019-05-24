package com.piekoszek.nowaksharedrent.payment;

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
public class MonthlyPaymentsController {

    private MonthlyPaymentsService monthlyPaymentsService;
    private JwtService jwtService;

    MonthlyPaymentsController(MonthlyPaymentsService monthlyPaymentsService, JwtService jwtService) {
        this.monthlyPaymentsService = monthlyPaymentsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/payment")
    ResponseEntity<Object> addPayment(@RequestBody Payment payment, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        monthlyPaymentsService.addPayment(payment, jwtData.getEmail());
        return new ResponseEntity<>(new MessageResponse("Payment added."), HttpStatus.CREATED);
    }
}
