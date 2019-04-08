package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class InviteController {

    private InviteService inviteService;
    private JwtService jwtService;

    InviteController(InviteService inviteService, JwtService jwtService) {
        this.inviteService = inviteService;
        this.jwtService = jwtService;
    }

    @PostMapping("/invite")
    ResponseEntity<Object> createInvite(@RequestBody Invite invite, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        inviteService.createInvite(jwtData.getEmail(), invite.getReceiver(), invite.getApartmentId());
        return new ResponseEntity<>("Invitation sent", HttpStatus.OK);
    }

    @PostMapping("/invite/resolve")
    ResponseEntity<Object> resolveInvite(@RequestParam("accepted") Boolean isAccepted, @RequestBody Invite invite, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        inviteService.resolveInvite(jwtData.getEmail(), invite.getApartmentId(), isAccepted);
        if (isAccepted) {
            return new ResponseEntity<>("Invitation accepted", HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invitation declined"), HttpStatus.OK);
    }
}