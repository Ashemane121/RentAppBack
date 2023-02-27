package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Rating;
import com.RentCars.RentCars.persistances.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }
}
