package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.InsuranceClaim;
import com.RentCars.RentCars.persistances.repositories.InsuranceClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceClaimServiceImpl implements InsuranceClaimService {

    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;

    @Override
    public InsuranceClaim createInsuranceClaim(InsuranceClaim insuranceClaim) {
        return insuranceClaimRepository.save(insuranceClaim);
    }

    @Override
    public InsuranceClaim getInsuranceClaimById(Long id) {
        return insuranceClaimRepository.findById(id).orElse(null);
    }
    @Override
    public List<InsuranceClaim> getAllInsuranceClaim() {
        return insuranceClaimRepository.findAll();
    }

    @Override
    public InsuranceClaim updateInsuranceClaim(InsuranceClaim insuranceClaim) {
        return insuranceClaimRepository.save(insuranceClaim);
    }

    @Override
    public void deleteInsuranceClaimById(Long id) {
        insuranceClaimRepository.deleteById(id);
    }
}
