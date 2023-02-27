package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.InsuranceClaim;

import java.util.List;

public interface InsuranceClaimService {
    InsuranceClaim createInsuranceClaim(InsuranceClaim insuranceClaim);

    InsuranceClaim getInsuranceClaimById(Long id);

    List<InsuranceClaim> getAllInsuranceClaim() ;

    InsuranceClaim updateInsuranceClaim(InsuranceClaim insuranceClaim);


    void deleteInsuranceClaimById(Long id);
}
