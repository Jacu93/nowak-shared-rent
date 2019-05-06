package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apartment")
class ApartmentController {

    private ApartmentService apartmentService;
    private JwtService jwtService;

    ApartmentController(ApartmentService apartmentService, JwtService jwtService) {
        this.apartmentService = apartmentService;
        this.jwtService = jwtService;
    }

    @PostMapping
    ResponseEntity<Object> createApartment(@RequestBody Apartment apartment, @RequestHeader("Authorization") String token) {
        JwtData jwtData = jwtService.readToken(token);
        apartmentService.createApartment(apartment.getAddress(), apartment.getCity(), jwtData.getEmail());
        return new ResponseEntity<>(new MessageResponse("Apartment created."), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getApartment(@PathVariable("id") String id) {
        return new ResponseEntity<>(apartmentService.getApartment(id), HttpStatus.OK);
    }
}