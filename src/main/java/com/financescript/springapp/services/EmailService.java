package com.financescript.springapp.services;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
