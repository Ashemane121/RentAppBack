package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Car;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.persistances.entities.User;

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

