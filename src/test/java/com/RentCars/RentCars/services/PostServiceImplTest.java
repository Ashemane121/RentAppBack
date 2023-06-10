package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Post;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.persistances.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Test
    void getAllPosts() {
        // Create a list of dummy posts
        List<Post> dummyPosts = new ArrayList<>();
        // Mock the behavior of postRepository.findAll() to return the dummy posts
        Mockito.when(postRepository.findAll()).thenReturn(dummyPosts);
        // Call the method under test
        List<Post> result = postService.getAllPosts();
        // Assert the result as needed
        assertEquals(dummyPosts, result);
    }

    @Test
    void getPostById() {
        // Create a dummy post
        Post dummyPost = new Post();
        // Mock the behavior of postRepository.findById() to return the dummy post
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(dummyPost));
        // Call the method under test
        Post result = postService.getPostById(1L);
        // Assert the result as needed
        assertEquals(dummyPost, result);
    }


    @Test
    void savePost() {
        // Create a dummy post
        Post dummyPost = new Post();
        // Mock the behavior of postRepository.save() to return the saved post
        Mockito.when(postRepository.save(dummyPost)).thenReturn(dummyPost);
        // Call the method under test
        postService.savePost(dummyPost);
        // Verify that the save method was called
        Mockito.verify(postRepository, Mockito.times(1)).save(dummyPost);
    }

    @Test
    void deletePost() {
        // Create a dummy post ID
        Long postId = 1L;
        // Call the method under test
        postService.deletePost(postId);
        // Verify that the deleteById method was called with the correct ID
        Mockito.verify(postRepository, Mockito.times(1)).deleteById(postId);
    }

    @Test
    void getPostsByUser() {
        // Create a dummy user ID
        Long userId = 1L;
        // Create a list of dummy posts
        List<Post> dummyPosts = new ArrayList<>();
        // Mock the behavior of postRepository.findByUser() to return the dummy posts
        Mockito.when(postRepository.findByUser(Mockito.any(User.class))).thenReturn(dummyPosts);
        // Call the method under test
        List<Post> result = postService.getPostsByUser(userId);
        // Assert the result as needed
        assertEquals(dummyPosts, result);
    }

    @Test
    void updatePost() {
        // Create a dummy post
        Post dummyPost = new Post();
        // Mock the behavior of postRepository.save() to return the updated post
        Mockito.when(postRepository.save(dummyPost)).thenReturn(dummyPost);
        // Call the method under test
        Post result = postService.updatePost(dummyPost);
        // Assert the result as needed
        assertEquals(dummyPost, result);
    }
}