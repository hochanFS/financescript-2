package com.financescript.springapp.services;

public interface PasswordTokenService {
    String validatePasswordToken(Long id, String token);
}
