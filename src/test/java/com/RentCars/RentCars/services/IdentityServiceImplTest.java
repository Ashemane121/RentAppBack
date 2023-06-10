package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Identity;
import com.RentCars.RentCars.persistances.repositories.IdentityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdentityServiceImplTest {

    @Mock
    private IdentityRepository identityRepository;

    @InjectMocks
    private IdentityServiceImpl identityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllIdentities() {
        // Create dummy identities
        Identity identity1 = new Identity();
        Identity identity2 = new Identity();
        List<Identity> identities = Arrays.asList(identity1, identity2);
        // Mock the behavior of the identityRepository
        Mockito.when(identityRepository.findAll()).thenReturn(identities);
        // Call the method under test
        List<Identity> result = identityService.getAllIdentities();
        // Verify the result
        assertEquals(identities, result);
    }

    @Test
    void getIdentityById() {
        // Create a dummy identity ID
        Long identityId = 1L;
        // Create a dummy identity
        Identity identity = new Identity();
        // Mock the behavior of the identityRepository
        Mockito.when(identityRepository.findById(identityId)).thenReturn(Optional.of(identity));
        // Call the method under test
        Identity result = identityService.getIdentityById(identityId);
        // Verify the result
        assertEquals(identity, result);
    }

    @Test
    void saveIdentity() {
        // Create a dummy identity
        Identity identity = new Identity();
        // Call the method under test
        identityService.saveIdentity(identity);
        // Verify that the save method was called with the correct identity
        Mockito.verify(identityRepository, Mockito.times(1)).save(identity);
    }

    @Test
    void deleteIdentity() {
        // Create a dummy identity ID
        Long identityId = 1L;
        // Call the method under test
        identityService.deleteIdentity(identityId);
        // Verify that the deleteById method was called with the correct ID
        Mockito.verify(identityRepository, Mockito.times(1)).deleteById(identityId);
    }

    @Test
    void getIdentitiesByUser() {
        // Create a dummy user ID
        Long userId = 1L;

        // Create dummy identities
        Identity identity1 = new Identity();
        Identity identity2 = new Identity();
        List<Identity> identities = Arrays.asList(identity1, identity2);
        // Mock the behavior of the identityRepository
        Mockito.when(identityRepository.findByUserId(userId)).thenReturn(identities);
        // Call the method under test
        List<Identity> result = identityService.getIdentitiesByUser(userId);
        // Verify the result
        assertEquals(identities, result);
    }

    @Test
    void updateIdentity() {
        // Create a dummy identity
        Identity identity = new Identity();
        // Call the method under test
        identityService.updateIdentity(identity);
        // Verify that the save method was called with the correct identity
        Mockito.verify(identityRepository, Mockito.times(1)).save(identity);
    }
}
