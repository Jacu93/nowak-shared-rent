package com.piekoszek.nowaksharedrent.apartment;

import com.piekoszek.nowaksharedrent.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class ApartmentController {

    private ApartmentService apartmentService;

    ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping("/apartment/new")
    ResponseEntity<Object> createApartment(@RequestBody Apartment apartment) {
        apartmentService.createApartment(apartment);
        return new ResponseEntity<>(new MessageResponse("Apartment created."), HttpStatus.CREATED);
    }

    @GetMapping("/apartment/get")
    @ResponseBody
    public List<Apartment> apartmentList(@RequestParam("owner") String email) {
        return apartmentService.getApartments(email);
    }
}