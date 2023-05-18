package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.persistances.entities.Claim;
import com.RentCars.RentCars.persistances.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Claim findByRental(Rental rental);
}
