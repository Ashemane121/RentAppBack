package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class StorageController {

    @Autowired
    private StorageService service;

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

}
