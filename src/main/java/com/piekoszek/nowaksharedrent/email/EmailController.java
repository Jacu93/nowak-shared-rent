package com.piekoszek.nowaksharedrent.email;

import com.piekoszek.nowaksharedrent.jwt.Jwt;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/email")
class EmailController {

    private EmailService emailService;

    @PostMapping("/send")
    ResponseEntity<Object> sendEmail(@RequestBody Email email, @Jwt JwtData jwtData) {
        emailService.sendSimpleMessage(email.getTo(), email.getSubject(), email.getText());
        return new ResponseEntity<>(new MessageResponse("Simple email sent."), HttpStatus.OK);
    }
}
