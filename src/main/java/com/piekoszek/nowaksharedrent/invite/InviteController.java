package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
class InviteController {

    private InviteService inviteService;
    private JwtService jwtService;

    InviteController(InviteService inviteService, JwtService jwtService) {
        this.inviteService = inviteService;
        this.jwtService = jwtService;
    }

    @PostMapping("/invite")
    ResponseEntity<Object> createInvitation(@RequestBody Invitation invitation, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        Optional<String> result = inviteService.createInvitation(jwtData.getEmail(), invitation.getReceiver(), invitation.getApartmentId());
        if (result.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(result.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invalid apartment/receiver or you're not and admin of this apartment!"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/invitation")
    ResponseEntity<Object> getInvitations(@RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        return new ResponseEntity<>(inviteService.getInvitations(jwtData.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/invitation")
    ResponseEntity<Object> resolveInvitation(@RequestBody Invitation invitation, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        Optional<String> result = inviteService.resolveInvitation(jwtData.getEmail(), invitation.getApartmentId(), invitation.isAccepted);
        if (result.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(result.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invitation not found!"), HttpStatus.BAD_REQUEST);
    }
}