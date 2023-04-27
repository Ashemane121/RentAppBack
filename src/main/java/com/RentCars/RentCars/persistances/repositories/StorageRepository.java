package com.RentCars.RentCars.persistances.repositories;

import com.RentCars.RentCars.persistances.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);
    Optional<ImageData> findByRef(String fileRef);
}
