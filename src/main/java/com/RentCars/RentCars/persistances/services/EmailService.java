package com.RentCars.RentCars.persistances.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}

