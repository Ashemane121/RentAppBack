package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.entities.Car;
import com.RentCars.RentCars.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser(User user);
}
