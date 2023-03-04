package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.Car;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.services.CarService;
import com.RentCars.RentCars.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carService.getCarById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    /*
    @PostMapping("")
    public ResponseEntity<Void> saveCar(@RequestBody Car car) {
        carService.saveCar(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id, @RequestBody Car car) {
        Car existingCar = carService.getCarById(id);
        if (existingCar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingCar.setBrand(car.getBrand());
        existingCar.setModel(car.getModel());
        existingCar.setMileage(car.getMileage());
        existingCar.setYear(car.getYear());
        existingCar.setDescription(car.getDescription());
        existingCar.setGearbox(car.getGearbox());
        existingCar.setFuel(car.getFuel());
        existingCar.setPrice(car.getPrice());
        existingCar.setAvailability(car.getAvailability());
        carService.updateCar(existingCar);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> getCarsByUser(@PathVariable Long userId) {
        List<Car> cars = carService.getCarsByUser(userId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> saveCarWithUser(@RequestBody Car car , @PathVariable Long userId) {
        User user=userService.getUserById(userId);
        car.setUser(user);
        carService.saveCar(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}


