package com.RentCars.RentCars.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfilePictureUrlRequest {

    private String email;
    private String profilePictureUrl;
}

