package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Car;
import com.RentCars.RentCars.entities.Request;
import com.RentCars.RentCars.entities.User;

import java.util.List;

public interface RequestService {

    List<Request> getAllRequests();

    List<Request> getRequestsByUser(User user);

    List<Request> getRequestsByCar(Car car);

    List<Request> getRequestsByStatus(String status);

    Request getRequestById(Long id);

    Request createRequest(Request request);

    Request updateRequest( Request request);

    void deleteRequest(Long id);

}

