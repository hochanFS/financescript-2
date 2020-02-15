package com.financescript.springapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class MiscellaneousControllerTest {
    MiscellaneousController controller;

    @BeforeEach
    void setUp() {
        controller = new MiscellaneousController();
    }

    @Test
    void termsOfUse() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/termsOfUse"))
                .andExpect(status().isOk())
                .andExpect(view().name("/misc/termsOfUse"));
    }
}
