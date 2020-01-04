package com.financescript.springapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GlobalControllerAdviceTest {
    GlobalControllerAdvice globalControllerAdvice;

    @BeforeEach
    void setUp() {
        globalControllerAdvice = new GlobalControllerAdvice();
    }

    @Test
    void populateUser_activePrincipal() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("USER1");

        assertEquals("USER1", globalControllerAdvice.populateUser(principal));
    }

    @Test
    void populateUser_nullPrincipal() {
        Principal principal = null;

        assertNull(globalControllerAdvice.populateUser(principal));
    }
}