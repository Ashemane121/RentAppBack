package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
