package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Claim;
import com.RentCars.RentCars.persistances.entities.Rental;
import com.RentCars.RentCars.persistances.repositories.ClaimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClaimServiceImplTest {

    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ClaimServiceImpl claimService;

    @BeforeEach
    void setUp() {
        // You can perform additional setup here before each test method
    }

    @Test
    void getAllClaims() {
        // Create a list of dummy claims
        List<Claim> dummyClaims = new ArrayList<>();
        // Mock the behavior of claimRepository.findAll() to return the dummy claims
        Mockito.when(claimRepository.findAll()).thenReturn(dummyClaims);
        // Call the method under test
        List<Claim> result = claimService.getAllClaims();
        // Assert the result as needed
        assertEquals(dummyClaims, result);
    }

    @Test
    void getClaimById() {
        // Create a dummy claim
        Claim dummyClaim = new Claim();
        // Mock the behavior of claimRepository.findById() to return the dummy claim
        Mockito.when(claimRepository.findById(1L)).thenReturn(Optional.of(dummyClaim));
        // Call the method under test
        Claim result = claimService.getClaimById(1L);
        // Assert the result as needed
        assertEquals(dummyClaim, result);
    }

    @Test
    void getClaimByRental() {
        // Create a dummy rental ID
        Long rentalId = 1L;
        // Create a dummy claim
        Claim dummyClaim = new Claim();
        // Mock the behavior of claimRepository.findByRental() to return the dummy claim
        Mockito.when(claimRepository.findByRental(Mockito.any(Rental.class))).thenReturn(dummyClaim);
        // Call the method under test
        Claim result = claimService.getClaimByRental(rentalId);
        // Assert the result as needed
        assertEquals(dummyClaim, result);
    }

    @Test
    void createClaim() {
        // Create a dummy claim
        Claim dummyClaim = new Claim();
        // Mock the behavior of claimRepository.save() to return the saved claim
        Mockito.when(claimRepository.save(dummyClaim)).thenReturn(dummyClaim);
        // Call the method under test
        Claim result = claimService.createClaim(dummyClaim);
        // Assert the result as needed
        assertEquals(dummyClaim, result);
    }

    @Test
    void updateClaim() {
        // Create a dummy claim
        Claim dummyClaim = new Claim();
        // Mock the behavior of claimRepository.save() to return the updated claim
        Mockito.when(claimRepository.save(dummyClaim)).thenReturn(dummyClaim);
        // Call the method under test
        Claim result = claimService.updateClaim(dummyClaim);
        // Assert the result as needed
        assertEquals(dummyClaim, result);
    }

    @Test
    void deleteClaimById() {
        // Create a dummy claim ID
        Long claimId = 1L;
        // Call the method under test
        claimService.deleteClaimById(claimId);
        // Verify that the deleteById method was called with the correct ID
        Mockito.verify(claimRepository, Mockito.times(1)).deleteById(claimId);
    }
}