package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Rental;

import java.util.List;

public interface RentalService {
    Rental createRental(Rental rental);
    List<Rental> getAllRentals() ;
    Rental getRentalById(Long id);
    Rental updateRental(Rental rental);

    void deleteRentalById(Long id);
}
