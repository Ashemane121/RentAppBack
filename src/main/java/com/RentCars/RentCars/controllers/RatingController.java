package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.Rating;
import com.RentCars.RentCars.persistances.entities.Rental;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.services.RatingService;
import com.RentCars.RentCars.services.RentalService;
import com.RentCars.RentCars.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RequestService requestService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Rating>> getAllRating() {
        List<Rating> rating = ratingService.getAllRating();
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        Rating rating = ratingService.getRatingById(id);
        if (rating != null) {
            return new ResponseEntity<>(rating, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/post/{postId}")
    public ResponseEntity<List<Rating>> getRatingsByPost (@PathVariable Long postId) {
        Post post = new Post();
        post.setId_post(postId);
        List<Request> requests = requestService.getRequestsByPost(post);
        List<Rating> ratings = new ArrayList<>();
        for (Request request : requests) {
            Rental rental = rentalService.getRentalByRequest(request.getId_request());
            if (rental != null) {
                Rating rating = ratingService.getRatingByRental(rental.getId_rental());
                if (rating != null) {
                    ratings.add(rating);
                }
            }
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/get/rental/{rentalId}")
    public ResponseEntity<Rating> getRatingByRental (@PathVariable Long rentalId) {
        Rental rental = rentalService.getRentalById(rentalId);
        Rating rating = ratingService.getRatingByRental(rental.getId_rental());
        if (rating != null) {
            return new ResponseEntity<>(rating, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<Void> createRating(@RequestBody Rating rating , @PathVariable Long rentalId) {
        Rental rental=rentalService.getRentalById(rentalId);
        rating.setRental(rental);
        ratingService.createRating(rating);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRating(@PathVariable Long id, @RequestBody Rating rating) {
        Rating existingRating = ratingService.getRatingById(id);

        if (existingRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRating.setStars(rating.getStars());
        existingRating.setComment(rating.getComment());
        ratingService.updateRating(existingRating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRatingById(@PathVariable Long id) {
        ratingService.deleteRatingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
