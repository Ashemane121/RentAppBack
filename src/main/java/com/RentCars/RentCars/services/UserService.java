package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void deleteUser(Long id);


    User updateUser(User user);

}