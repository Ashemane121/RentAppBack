package com.RentCars.RentCars.persistances.services;

import com.RentCars.RentCars.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    PasswordResetToken createPasswordResetToken(String email);
    Optional<PasswordResetToken> findByToken(String token);
    void deletePasswordResetToken(PasswordResetToken passwordResetToken);
}

