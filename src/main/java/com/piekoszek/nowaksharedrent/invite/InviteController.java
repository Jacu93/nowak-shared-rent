package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InviteController {

    private InviteService inviteService;

    InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping("/invite")
    ResponseEntity<Object> createInvite(@RequestBody Invite invite) {
        inviteService.createInvite(invite.getSender(), invite.getReceiver(), invite.getApartmentId());
        return new ResponseEntity<>("Invitation sent", HttpStatus.OK);
        //return new ResponseEntity<>(new MessageResponse("Existing invitation is still pending!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/invite/resolve")
    ResponseEntity<Object> resolveInvite(@RequestParam("accepted") Boolean isAccepted, @RequestBody Invite invite) {
        if (inviteService.isAcceptedInvite(invite.getReceiver(), invite.getApartmentId(), isAccepted)) {
            return new ResponseEntity<>("Invitation accepted", HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Invitation declined"), HttpStatus.OK);
    }
}
