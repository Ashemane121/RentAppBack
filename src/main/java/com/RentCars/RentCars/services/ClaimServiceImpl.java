package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Claim;
import com.RentCars.RentCars.persistances.entities.Rental;
import com.RentCars.RentCars.persistances.repositories.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService{

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim getClaimById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    @Override
    public Claim getClaimByRental(Long rentalId) {
        Rental rental = new Rental();
        rental.setId_rental(rentalId);
        return claimRepository.findByRental(rental);
    }

    @Override
    public Claim createClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public Claim updateClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public void deleteClaimById(Long id) {
        claimRepository.deleteById(id);
    }
}
