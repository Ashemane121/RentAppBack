package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.persistances.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> getRequestsByUser(User user) {
        return requestRepository.findByUser(user);
    }

    @Override
    public List<Request> getRequestsByPost(Post post) {
        return requestRepository.findByPost(post);
    }

    @Override
    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request updateRequest(Request request) {
        return requestRepository.save(request);

    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

}

