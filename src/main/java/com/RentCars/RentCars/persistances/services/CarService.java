package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    Car getCarById(Long id);
    void saveCar(Car car);
    void deleteCar(Long id);
    List<Car> getCarsByUser(Long userId);

    Car updateCar(Car car);
}
