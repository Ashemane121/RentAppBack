package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.entities.InsuranceClaim;
import com.RentCars.RentCars.entities.Rental;
import com.RentCars.RentCars.persistances.services.InsuranceClaimService;
import com.RentCars.RentCars.persistances.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insuranceClaims")
public class InsuranceClaimController {

    @Autowired
    private InsuranceClaimService insuranceClaimService;

    @Autowired
    private RentalService rentalService;


    @GetMapping
    public ResponseEntity<List<InsuranceClaim>> getAllInsuranceClaims() {
        List<InsuranceClaim> insuranceClaims = insuranceClaimService.getAllInsuranceClaim();
        return new ResponseEntity<>(insuranceClaims, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceClaim> getInsuranceClaimById(@PathVariable Long id) {
        InsuranceClaim insuranceClaim = insuranceClaimService.getInsuranceClaimById(id);
        if (insuranceClaim != null) {
            return new ResponseEntity<>(insuranceClaim, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<Void> createInsuranceClaim(@RequestBody InsuranceClaim insuranceClaim , @PathVariable Long rentalId) {
        Rental rental=rentalService.getRentalById(rentalId);
        insuranceClaim.setRental(rental);
        insuranceClaimService.createInsuranceClaim(insuranceClaim);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceClaim> updateInsuranceClaim(@PathVariable Long id, @RequestBody InsuranceClaim insuranceClaim) {
        InsuranceClaim currentInsuranceClaim = insuranceClaimService.getInsuranceClaimById(id);
        if (currentInsuranceClaim == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentInsuranceClaim.setDate(insuranceClaim.getDate());
        currentInsuranceClaim.setDescription(insuranceClaim.getDescription());
        currentInsuranceClaim.setAmount(insuranceClaim.getAmount());
        InsuranceClaim updatedInsuranceClaim = insuranceClaimService.updateInsuranceClaim(currentInsuranceClaim);
        return new ResponseEntity<>(updatedInsuranceClaim, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInsuranceClaimById(@PathVariable Long id) {
        InsuranceClaim insuranceClaim = insuranceClaimService.getInsuranceClaimById(id);
        if (insuranceClaim != null) {
            insuranceClaimService.deleteInsuranceClaimById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
