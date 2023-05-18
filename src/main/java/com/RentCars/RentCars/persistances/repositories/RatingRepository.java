package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.persistances.entities.Rating;
import com.RentCars.RentCars.persistances.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    //    Rental findByRequest(Request request);
    Rating findByRental(Rental rental);
}
