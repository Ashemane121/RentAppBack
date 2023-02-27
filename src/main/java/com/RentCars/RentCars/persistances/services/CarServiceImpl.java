package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.Car;
import com.RentCars.RentCars.entities.User;
import com.RentCars.RentCars.persistances.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> getCarsByUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return carRepository.findByUser(user);
    }

    @Override
    public Car updateCar(Car car) {
        return carRepository.save(car);

    }


}
