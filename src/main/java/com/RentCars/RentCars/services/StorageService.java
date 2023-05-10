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

        Optional<ImageData> imageDataOptional = repository.findByRef(ref);

        if (imageDataOptional.isPresent()) {
            // If an image with the same ref exists, update it with the new image data
            ImageData imageData = imageDataOptional.get();
            imageData.setName(file.getOriginalFilename());
            imageData.setType(file.getContentType());
            imageData.setImageData(ImageUtils.compressImage(file.getBytes()));
            repository.save(imageData);
            return "file uploaded successfully: " + file.getOriginalFilename();
        } else {
            // If an image with the same ref doesn't exist, save the new image
            ImageData imageData = ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .ref(ref)
                    .imageData(ImageUtils.compressImage(file.getBytes()))
                    .build();
            repository.save(imageData);
            return "file uploaded successfully: " + file.getOriginalFilename();
        }
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

    public void deleteImage(Long id) {
        repository.deleteById(id);
    }
}