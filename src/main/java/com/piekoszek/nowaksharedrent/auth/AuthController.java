package com.piekoszek.nowaksharedrent.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

@RestController
class AuthController {

    private AuthService authService;
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    ResponseEntity<Object> createAccount(@RequestBody Account account) {
        Optional<String> token = authService.createAccount(account);
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        if (constraintViolations.size()>0) {
            return new ResponseEntity<>(new MessageResponse(constraintViolations.iterator().next().getMessage()), HttpStatus.BAD_REQUEST);
        }
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody Account account) {
        Optional<String> token = authService.loginUser(account);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }
}