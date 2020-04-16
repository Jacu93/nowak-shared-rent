package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
        Optional<String> token = authService.createAccount(account);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new MessageResponse("Account with such email already exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody Account account) {
        Optional<String> token = authService.loginUser(account);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid email or password!"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/update")
    ResponseEntity<Object> updateTokenData(@RequestHeader("Authorization") String oldToken) {
        Optional<String> token = authService.refreshToken(oldToken);
        if (token.isPresent()) {
            return new ResponseEntity<>(new TokenResponse(token.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Token malformed or expired. Please login again."), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("reset-password")
    ResponseEntity<Object> resetPassword(@RequestBody Account account) {
        authService.resetPassword(account.getEmail());
        return new ResponseEntity<>(new MessageResponse("Reset link sent!"), HttpStatus.OK);
    }

    @PostMapping("set-password")
    ResponseEntity<Object> setPassword(@RequestBody Account account) {
        authService.setPassword(account);
        return new ResponseEntity<>(new MessageResponse("New password set!"), HttpStatus.OK);
    }
}