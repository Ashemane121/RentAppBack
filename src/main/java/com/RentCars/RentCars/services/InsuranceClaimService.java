package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.InsuranceClaim;

import java.util.List;

public interface InsuranceClaimService {
    InsuranceClaim createInsuranceClaim(InsuranceClaim insuranceClaim);

    InsuranceClaim getInsuranceClaimById(Long id);

    List<InsuranceClaim> getAllInsuranceClaim() ;

    InsuranceClaim updateInsuranceClaim(InsuranceClaim insuranceClaim);


    void deleteInsuranceClaimById(Long id);
}
