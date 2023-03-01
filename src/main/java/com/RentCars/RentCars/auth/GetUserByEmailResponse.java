package com.RentCars.RentCars.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByEmailResponse {

    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private Integer phone;
}
