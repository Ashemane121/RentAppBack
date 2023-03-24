package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.Request;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.services.PostService;
import com.RentCars.RentCars.services.RequestService;
import com.RentCars.RentCars.services.UserService;
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
    private PostService postService;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Request>> getRequestsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<Request> requests = requestService.getRequestsByUser(user);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Request>> getRequestsByPost(@PathVariable Long postId) {
        Post post = new Post();
        post.setId_post(postId);
        List<Request> requests = requestService.getRequestsByPost(post);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        List<Request> requests = requestService.getRequestsByStatus(status);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Void> createRequest(@RequestBody Request request , @PathVariable Long userId, @PathVariable Long postId) {
        User user=userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        request.setUser(user);
        request.setPost(post);
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
        existingRequest.setPayment_method(request.getPayment_method());
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
