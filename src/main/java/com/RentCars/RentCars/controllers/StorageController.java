package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.ImageData;
import com.RentCars.RentCars.persistances.repositories.StorageRepository;
import com.RentCars.RentCars.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class StorageController {

    @Autowired
    private StorageService service;

    @Autowired
    private StorageRepository repository;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("ref") String ref) throws IOException {
        String uploadImage = service.uploadImage(file,ref);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/name/{fileName}")
    public ResponseEntity<?> downloadImageByName(@PathVariable String fileName){
        byte[] imageData=service.downloadImageByName(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @GetMapping("/ref/{fileRef}")
    public ResponseEntity<?> downloadImageByRef(@PathVariable String fileRef){
        byte[] imageData=service.downloadImageByRef(fileRef);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @DeleteMapping("/ref/{fileRef}")
    public ResponseEntity<?> deleteImageByRef(@PathVariable String fileRef) {
        Optional<ImageData> imageDataOptional = repository.findByRef(fileRef);

        if (imageDataOptional.isPresent()) {
            repository.deleteById(imageDataOptional.get().getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("File deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("File not found");
        }
    }


}
