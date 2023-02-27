package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void deleteUser(Long id);


    User updateUser(User user);

}