package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating getRatingById(Long id);
    Rating getRatingByRental(Long rentalId);
    Rating createRating(Rating rating);
    Rating updateRating(Rating rating);
    List<Rating> getAllRating() ;
    void deleteRatingById(Long id);
}
