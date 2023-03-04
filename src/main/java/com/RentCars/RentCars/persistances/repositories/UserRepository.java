package com.RentCars.RentCars.persistances.repositories;

import java.util.Optional;

import com.RentCars.RentCars.persistances.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
