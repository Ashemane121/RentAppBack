package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Rental;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.persistances.repositories.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRental() {
        // Create a dummy rental
        Rental rental = new Rental();
        // Mock the behavior of the rentalRepository
        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        // Call the method under test
        Rental result = rentalService.createRental(rental);
        // Verify the result
        assertEquals(rental, result);
    }

    @Test
    void getAllRentals() {
        // Create a list of dummy rentals
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental());
        rentals.add(new Rental());
        // Mock the behavior of the rentalRepository
        Mockito.when(rentalRepository.findAll()).thenReturn(rentals);
        // Call the method under test
        List<Rental> result = rentalService.getAllRentals();
        // Verify the result
        assertEquals(rentals, result);
    }

    @Test
    void getRentalById() {
        // Create a dummy rental ID
        Long rentalId = 1L;
        // Create a dummy rental
        Rental rental = new Rental();
        // Mock the behavior of the rentalRepository
        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        // Call the method under test
        Rental result = rentalService.getRentalById(rentalId);
        // Verify the result
        assertEquals(rental, result);
    }

    @Test
    void getRentalByRequest() {
        // Create a dummy request ID
        Long requestId = 1L;
        // Create a dummy request
        Request request = new Request();
        request.setId_request(requestId);
        // Create a dummy rental
        Rental rental = new Rental();
        // Mock the behavior of the rentalRepository
        Mockito.when(rentalRepository.findByRequest(Mockito.any(Request.class))).thenReturn(rental);
        // Call the method under test
        Rental result = rentalService.getRentalByRequest(requestId);
        // Verify the result
        assertEquals(rental, result);
    }

    @Test
    void updateRental() {
        // Create a dummy rental
        Rental rental = new Rental();
        // Mock the behavior of the rentalRepository
        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        // Call the method under test
        Rental result = rentalService.updateRental(rental);
        // Verify the result
        assertEquals(rental, result);
    }

    @Test
    void deleteRentalById() {
        // Create a dummy rental ID
        Long rentalId = 1L;
        // Call the method under test
        rentalService.deleteRentalById(rentalId);
        // Verify the interaction with the rentalRepository
        Mockito.verify(rentalRepository, Mockito.times(1)).deleteById(rentalId);
    }

}