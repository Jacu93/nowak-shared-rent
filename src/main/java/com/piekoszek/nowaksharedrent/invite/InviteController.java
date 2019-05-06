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
        inviteService.createInvitation(jwtData.getEmail(), invitation.getReceiver(), invitation.getApartmentId());
        return new ResponseEntity<>(new MessageResponse("Invitation sent."), HttpStatus.OK);
    }

    @GetMapping("/invitation")
    ResponseEntity<Object> getInvitations(@RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        return new ResponseEntity<>(inviteService.getInvitations(jwtData.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/invitation/{id}/accept")
    ResponseEntity<Object> acceptInvitation(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        Optional<String> result = inviteService.resolveInvitation(jwtData.getEmail(), id, true);
        if (result.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(result.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invitation not found!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/invitation/{id}/reject")
    ResponseEntity<Object> rejectInvitation(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        Optional<String> result = inviteService.resolveInvitation(jwtData.getEmail(), id, false);
        if (result.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(result.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invitation not found!"), HttpStatus.BAD_REQUEST);
    }
}