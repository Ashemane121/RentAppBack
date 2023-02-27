package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Rental;
import com.RentCars.RentCars.persistances.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
    @Override
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    @Override
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);

    }

    @Override
    public void deleteRentalById(Long id) {
        rentalRepository.deleteById(id);
    }
}

