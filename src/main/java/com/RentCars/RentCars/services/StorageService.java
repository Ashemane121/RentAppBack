package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.ImageData;
import com.RentCars.RentCars.persistances.repositories.StorageRepository;
import com.RentCars.RentCars.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file, String ref) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .ref(ref)
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImageByName(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    public byte[] downloadImageByRef(String fileRef){
        Optional<ImageData> dbImageData = repository.findByRef(fileRef);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}