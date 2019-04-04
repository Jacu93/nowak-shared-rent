package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class ApartmentController {

    private ApartmentService apartmentService;
    private JwtService jwtService;

    ApartmentController(ApartmentService apartmentService, JwtService jwtService) {
        this.apartmentService = apartmentService;
        this.jwtService = jwtService;
    }

    @PostMapping("/apartment")
    ResponseEntity<Object> createApartment(@RequestBody Apartment apartment, @RequestHeader HttpHeaders header) {
        try {
            if (header.get("Authorization") == null) {
                throw new InvalidTokenException("Missing authorization in header");
            }
            String token = header.get("Authorization").toString();
            jwtService.validateToken(token);
            JwtData jwtData = jwtService.readToken(token);
            apartmentService.createApartment(apartment.getAddress(), apartment.getCity(), jwtData.getEmail());
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse("Apartment created."), HttpStatus.CREATED);
    }

    @GetMapping("/apartment")
    ResponseEntity<Object> getApartment(@RequestParam("id") String id, @RequestHeader HttpHeaders header) {
        try {
            if (header.get("Authorization") == null) {
                throw new InvalidTokenException("Missing authorization in header");
            }
            String token = header.get("Authorization").toString();
            jwtService.validateToken(token);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(apartmentService.getApartment(id).toString(), HttpStatus.OK);
    }
}