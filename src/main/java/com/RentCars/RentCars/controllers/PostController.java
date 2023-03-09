package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.services.PostService;
import com.RentCars.RentCars.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> savePostWithUser(@RequestBody Post post, @PathVariable Long userId) {
        User user=userService.getUserById(userId);
        post.setUser(user);
        postService.savePost(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingPost.setBrand(post.getBrand());
        existingPost.setModel(post.getModel());
        existingPost.setMileage(post.getMileage());
        existingPost.setYear(post.getYear());
        existingPost.setDescription(post.getDescription());
        existingPost.setGearbox(post.getGearbox());
        existingPost.setFuel(post.getFuel());
        existingPost.setPrice(post.getPrice());
        existingPost.setAvailability(post.getAvailability());
        existingPost.setStatus(post.getStatus());
        postService.updatePost(existingPost);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }




}


