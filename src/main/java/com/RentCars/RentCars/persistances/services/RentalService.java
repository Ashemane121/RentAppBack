package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Rental;

import java.util.List;

public interface RentalService {
    Rental createRental(Rental rental);
    List<Rental> getAllRentals() ;
    Rental getRentalById(Long id);
    Rental updateRental(Rental rental);

    void deleteRentalById(Long id);
}
