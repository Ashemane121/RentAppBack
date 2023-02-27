package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.entities.Rental;
import com.RentCars.RentCars.entities.Request;
import com.RentCars.RentCars.persistances.services.RentalService;
import com.RentCars.RentCars.persistances.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RequestService requestService;

    @GetMapping("")
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental != null) {
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{requestId}")
    public ResponseEntity<Void> createRental(@RequestBody Rental rental , @PathVariable Long requestId) {
        Request request=requestService.getRequestById(requestId);
        rental.setRequest(request);
        rentalService.createRental(rental);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        Rental existingRental = rentalService.getRentalById(id);
        if (existingRental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRental.setStatus(rental.getStatus());
        existingRental.setPayment_method(rental.getPayment_method());
        rentalService.updateRental(existingRental);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalById(@PathVariable Long id) {
        rentalService.deleteRentalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

