package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.entities.Car;
import com.RentCars.RentCars.entities.Request;
import com.RentCars.RentCars.entities.User;
import com.RentCars.RentCars.persistances.services.CarService;
import com.RentCars.RentCars.persistances.services.RequestService;
import com.RentCars.RentCars.persistances.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Request request = requestService.getRequestById(id);
        if (request == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    /* @GetMapping("/user/{userId}")
    public ResponseEntity<List<Request>> getRequestsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Request> requests = requestService.getRequestsByUser(user);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }



    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Request>> getRequestsByCar(@PathVariable Long carId) {
        Car car = new Car();
        car.setIdcar(carId);
        List<Request> requests = requestService.getRequestsByCar(car);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        List<Request> requests = requestService.getRequestsByStatus(status);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    */
    /*@PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

     */
    @PostMapping("/{userId}/{carId}")
    public ResponseEntity<Void> createRequest(@RequestBody Request request , @PathVariable Long userId, @PathVariable Long carId) {
        User user=userService.getUserById(userId);
        Car car=carService.getCarById(carId);
        request.setUser(user);
        request.setCar(car);
        requestService.createRequest(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRequest(@PathVariable Long id, @RequestBody Request request) {
        Request existingRequest = requestService.getRequestById(id);
        if (existingRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRequest.setStart_date(request.getStart_date());
        existingRequest.setEnd_date(request.getEnd_date());
        existingRequest.setStatus(request.getStatus());
        requestService.updateRequest(existingRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        Request request = requestService.getRequestById(id);
        if (request == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
