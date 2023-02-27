package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating getRatingById(Long id);
    Rating createRating(Rating rating);
    Rating updateRating(Rating rating);
    List<Rating> getAllRating() ;
    void deleteRatingById(Long id);
}
