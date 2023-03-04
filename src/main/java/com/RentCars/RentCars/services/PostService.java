package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void savePost(Post post);
    void deletePost(Long id);
    List<Post> getPostsByUser(Long userId);

    Post updatePost(Post post);
}
