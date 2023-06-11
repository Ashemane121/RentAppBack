package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.Identity;
import com.RentCars.RentCars.persistances.repositories.IdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentityServiceImpl implements IdentityService{
    @Autowired
    private IdentityRepository identityRepository;

    @Override
    public List<Identity> getAllIdentities() {
        return identityRepository.findAll();
    }

    @Override
    public Identity getIdentityById(Long id) {
        return identityRepository.findById(id).orElse(null);
    }

    @Override
    public void saveIdentity(Identity identity) {
        identityRepository.save(identity);
    }

    @Override
    public void deleteIdentity(Long id) {
        identityRepository.deleteById(id);
    }

    @Override
    public List<Identity> getIdentitiesByUser(Long userId) {
        return identityRepository.findByUserId(userId);
    }

    @Override
    public Identity updateIdentity(Identity identity) {
        return identityRepository.save(identity);
    }
}
