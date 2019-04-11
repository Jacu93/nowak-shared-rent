package com.piekoszek.nowaksharedrent.apartment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
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
    ResponseEntity<Object> createApartment(@RequestBody Apartment apartment, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        apartmentService.createApartment(apartment.getAddress(), apartment.getCity(), jwtData.getEmail());
        return new ResponseEntity<>(new MessageResponse("Apartment created."), HttpStatus.CREATED);
    }

    @GetMapping("/apartment")
    ResponseEntity<Object> getApartment(@RequestParam("id") String id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String apartmentDetails = mapper.writeValueAsString(apartmentService.getApartment(id));
            return new ResponseEntity<>(apartmentDetails, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new MessageResponse("Error"), HttpStatus.BAD_REQUEST);
    }
}