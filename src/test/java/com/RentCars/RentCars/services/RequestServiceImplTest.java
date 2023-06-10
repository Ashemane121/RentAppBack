package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.persistances.repositories.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRequests() {
        // Create a list of dummy requests
        List<Request> requests = Arrays.asList(new Request(), new Request(), new Request());
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.findAll()).thenReturn(requests);
        // Call the method under test
        List<Request> result = requestService.getAllRequests();
        // Verify the result
        assertEquals(requests, result);
    }

    @Test
    void getRequestsByUser() {
        // Create a dummy user
        User user = new User();
        // Create a list of dummy requests
        List<Request> requests = Arrays.asList(new Request(), new Request());
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.findByUser(user)).thenReturn(requests);
        // Call the method under test
        List<Request> result = requestService.getRequestsByUser(user);
        // Verify the result
        assertEquals(requests, result);
    }

    @Test
    void getRequestsByPost() {
        // Create a dummy post
        Post post = new Post();
        // Create a list of dummy requests
        List<Request> requests = Arrays.asList(new Request(), new Request());
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.findByPost(post)).thenReturn(requests);
        // Call the method under test
        List<Request> result = requestService.getRequestsByPost(post);
        // Verify the result
        assertEquals(requests, result);
    }

    @Test
    void getRequestsByStatus() {
        // Create a dummy status
        String status = "Pending";
        // Create a list of dummy requests
        List<Request> requests = Arrays.asList(new Request(), new Request());
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.findByStatus(status)).thenReturn(requests);
        // Call the method under test
        List<Request> result = requestService.getRequestsByStatus(status);
        // Verify the result
        assertEquals(requests, result);
    }

    @Test
    void getRequestById() {
        // Create a dummy request ID
        Long requestId = 1L;
        // Create a dummy request
        Request request = new Request();
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
        // Call the method under test
        Request result = requestService.getRequestById(requestId);
        // Verify the result
        assertEquals(request, result);
    }

    @Test
    void createRequest() {
        // Create a dummy request
        Request request = new Request();
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.save(Mockito.any(Request.class))).thenReturn(request);
        // Call the method under test
        Request result = requestService.createRequest(request);
        // Verify the result
        assertEquals(request, result);
    }


    @Test
    void updateRequest() {
        // Create a dummy request
        Request request = new Request();
        // Mock the behavior of the requestRepository
        Mockito.when(requestRepository.save(Mockito.any(Request.class))).thenReturn(request);
        // Call the method under test
        Request result = requestService.updateRequest(request);
        // Verify the result
        assertEquals(request, result);
    }

    @Test
    void deleteRequestById() {
        // Create a dummy request ID
        Long requestId = 1L;
        // Call the method under test
        requestService.deleteRequest(requestId);
        // Verify the interaction with the requestRepository
        Mockito.verify(requestRepository, Mockito.times(1)).deleteById(requestId);
    }

}