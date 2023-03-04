package com.RentCars.RentCars.services;

import com.RentCars.RentCars.persistances.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    PasswordResetToken createPasswordResetToken(String email);
    Optional<PasswordResetToken> findByToken(String token);
    void deletePasswordResetToken(PasswordResetToken passwordResetToken);
}

