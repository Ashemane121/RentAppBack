package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Identity;

import java.util.List;

public interface IdentityService {
    List<Identity> getAllIdentities();
    Identity getIdentityById(Long id);
    void saveIdentity(Identity identity);
    void deleteIdentity(Long id);
    List<Identity> getIdentitiesByUser(Long userId);

    Identity updateIdentity(Identity identity);
}
