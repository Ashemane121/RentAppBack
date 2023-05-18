package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.*;
import com.RentCars.RentCars.services.ClaimService;
import com.RentCars.RentCars.services.RentalService;
import com.RentCars.RentCars.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    @Autowired
    private ClaimService claimService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RequestService requestService;

    @GetMapping("")
    public ResponseEntity<List<Claim>> getAllClaims() {
        List<Claim> claims = claimService.getAllClaims();
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable Long id) {
        Claim claim = claimService.getClaimById(id);
        if (claim != null) {
            return new ResponseEntity<>(claim, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Claim>> getClaimsByPost (@PathVariable Long postId) {
        Post post = new Post();
        post.setId_post(postId);
        List<Request> requests = requestService.getRequestsByPost(post);
        List<Claim> claims = new ArrayList<>();
        for (Request request : requests) {
            Rental rental = rentalService.getRentalByRequest(request.getId_request());
            if (rental != null) {
                Claim claim = claimService.getClaimByRental(rental.getId_rental());
                if (claim != null) {
                    claims.add(claim);
                }
            }
        }
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    @GetMapping("/rental/{rentalId}")
    public ResponseEntity<Claim> getClaimByRental (@PathVariable Long rentalId) {
        Rental rental = rentalService.getRentalById(rentalId);
        Claim claim = claimService.getClaimByRental(rental.getId_rental());
        if (claim != null) {
            return new ResponseEntity<>(claim, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{rentalId}")
    public ResponseEntity<Void> createClaim(@RequestBody Claim claim , @PathVariable Long rentalId) {
        Rental rental=rentalService.getRentalById(rentalId);
        claim.setRental(rental);
        claimService.createClaim(claim);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClaim(@PathVariable Long id, @RequestBody Claim claim) {
        Claim existingClaim = claimService.getClaimById(id);

        if (existingClaim == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingClaim.setStatus(claim.getStatus());
        existingClaim.setType(claim.getType());
        existingClaim.setSubject(claim.getSubject());
        claimService.updateClaim(existingClaim);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaimById(@PathVariable Long id) {
        claimService.deleteClaimById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
