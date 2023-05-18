package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Claim;

import java.util.List;

public interface ClaimService {
    List<Claim> getAllClaims();
    Claim getClaimById(Long id);
    Claim getClaimByRental(Long rentalId);
    Claim createClaim(Claim claim);
    Claim updateClaim(Claim claim);
    void deleteClaimById(Long id);
}
