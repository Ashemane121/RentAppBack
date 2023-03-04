package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.persistances.entities.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    List<Identity> findByUserId(Long userId);
}
