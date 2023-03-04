package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.persistances.entities.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, Long> {
}
