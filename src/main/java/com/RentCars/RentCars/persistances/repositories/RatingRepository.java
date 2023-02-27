package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
