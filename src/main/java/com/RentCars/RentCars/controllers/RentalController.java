package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.Rental;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.services.RentalService;
import com.RentCars.RentCars.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RequestService requestService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental != null) {
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/request/{requestId}")
    public ResponseEntity<Rental> getRentalByRequest(@PathVariable Long requestId) {
        // Request request = new Request();
        // request.setId_request(requestId);
        Rental rental = rentalService.getRentalByRequest(requestId);
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    @GetMapping("/get/post/{postId}")
    public ResponseEntity<List<Rental>> getRentalsByPost(@PathVariable Long postId) {
        Post post = new Post();
        post.setId_post(postId);
        List<Request> requests = requestService.getRequestsByPost(post);
        List<Rental> rentals = new ArrayList<>();
        for (Request request : requests) {
            Rental rental = rentalService.getRentalByRequest(request.getId_request());
            if (rental != null) {
                rentals.add(rental);
            }
        }
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Request> requests = requestService.getRequestsByUser(user);
        List<Rental> rentals = new ArrayList<>();
        for (Request request : requests) {
            Rental rental = rentalService.getRentalByRequest(request.getId_request());
            if (rental != null) {
                rentals.add(rental);
            }
        }
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }



    @PostMapping("/{requestId}")
    public ResponseEntity<Void> createRental(@RequestBody Rental rental , @PathVariable Long requestId) {
        Request request=requestService.getRequestById(requestId);
        if (request == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        rentalService.updateRental(existingRental);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalById(@PathVariable Long id) {
        rentalService.deleteRentalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

