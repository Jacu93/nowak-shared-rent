package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.jwt.Jwt;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/apartment")
class ApartmentController {

    private ApartmentService apartmentService;

    @PostMapping
    ResponseEntity<Object> createApartment(@RequestBody Apartment apartment, @Jwt JwtData jwtData) {
        apartmentService.createApartment(apartment.getAddress(), apartment.getCity(), jwtData.getEmail());
        return new ResponseEntity<>(new MessageResponse("Apartment created."), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getApartment(@PathVariable("id") String id) {
        return new ResponseEntity<>(apartmentService.getApartment(id), HttpStatus.OK);
    }

    @PostMapping("/rent/{id}")
    ResponseEntity<Object> updateRent(@PathVariable("id") String id, @RequestBody Rent newRent) {
        apartmentService.updateRent(id, newRent);
        return new ResponseEntity<>(new MessageResponse("Rent value updated."), HttpStatus.OK);
    }
}