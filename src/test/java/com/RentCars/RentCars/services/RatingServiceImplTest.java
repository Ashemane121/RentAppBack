package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Rating;
import com.RentCars.RentCars.persistances.entities.Rental;
import com.RentCars.RentCars.persistances.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRatingById() {
        // Create a dummy rating ID
        Long ratingId = 1L;
        // Create a dummy rating
        Rating rating = new Rating();
        // Mock the behavior of the ratingRepository
        Mockito.when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        // Call the method under test
        Rating result = ratingService.getRatingById(ratingId);
        // Verify the result
        assertEquals(rating, result);
    }

    @Test
    void getRatingByRental() {
        // Create a dummy rental ID
        Long rentalId = 1L;
        // Create a dummy rental
        Rental rental = new Rental();
        rental.setId_rental(rentalId);
        // Create a dummy rating
        Rating rating = new Rating();
        // Mock the behavior of the ratingRepository
        Mockito.when(ratingRepository.findByRental(Mockito.any(Rental.class))).thenReturn(rating);
        // Call the method under test
        Rating result = ratingService.getRatingByRental(rentalId);
        // Verify the result
        assertEquals(rating, result);
    }


    @Test
    void getAllRating() {
        // Create dummy ratings
        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        List<Rating> ratings = Arrays.asList(rating1, rating2);
        // Mock the behavior of the ratingRepository
        Mockito.when(ratingRepository.findAll()).thenReturn(ratings);
        // Call the method under test
        List<Rating> result = ratingService.getAllRating();
        // Verify the result
        assertEquals(ratings, result);
    }

    @Test
    void createRating() {
        // Create a dummy rating
        Rating rating = new Rating();
        // Call the method under test
        ratingService.createRating(rating);
        // Verify that the save method was called with the correct rating
        Mockito.verify(ratingRepository, Mockito.times(1)).save(rating);
    }

    @Test
    void updateRating() {
        // Create a dummy rating
        Rating rating = new Rating();
        // Call the method under test
        ratingService.updateRating(rating);
        // Verify that the save method was called with the correct rating
        Mockito.verify(ratingRepository, Mockito.times(1)).save(rating);
    }

    @Test
    void deleteRatingById() {
        // Create a dummy rating ID
        Long ratingId = 1L;
        // Call the method under test
        ratingService.deleteRatingById(ratingId);
        // Verify that the deleteById method was called with the correct ID
        Mockito.verify(ratingRepository, Mockito.times(1)).deleteById(ratingId);
    }
}
