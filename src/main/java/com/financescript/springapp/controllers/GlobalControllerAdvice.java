package com.financescript.springapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("sessionUsername")
    public String populateUser(Principal principal) {
        String sessionUsername = null;
        if (principal != null)
            sessionUsername = principal.getName();
        return sessionUsername;
    }
}
