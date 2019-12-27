package com.financescript.springapp.controllers;

import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class SecurityControllerTest {

    @Mock
    private MemberService memberService;

    @Mock
    private BindingResult mockBindingResult;

    MemberDto memberDto;

    SecurityController controller;

    MockMvc mockMvc;

    BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new SecurityController(memberService);
        memberDto = new MemberDto();
        memberDto.setEmail("test1@gmail.com");
        memberDto.setPassword("test123");
        memberDto.setUsername("user1");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void registerMemberAccount() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("member"));
    }

    @Test
    void processSignUpFormWithValidationFail() throws Exception {
        when(mockBindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/register"))
                .andExpect(status().isOk());
    }

    @Test
    void processSignUpFormWithValidationSuccess() throws Exception {
        when(mockBindingResult.hasErrors()).thenReturn(false);
        mockMvc.perform(post("/register"))
                .andExpect(status().isOk());
    }

    @Test
    void signInForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}