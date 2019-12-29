package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SecurityControllerTest {

    @Spy
    private BindingResult mockBindingResult;

    @Mock
    private MemberService memberService;

    @Mock
    private Model model;

    MemberDto memberDto;

    SecurityController controller;

    MockMvc mockMvc;

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
    void processSignUpStatus() throws Exception {
        mockMvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/sign-up"));
    }

    @Test
    void processSignUpFormWithValidationFail() {
        when(memberService.findByUsername(any())).thenReturn(null);
        when(memberService.findByEmail(any())).thenReturn(null);
        doReturn(true).when(mockBindingResult).hasErrors();
        assertEquals("security/sign-up", controller.processSignUpForm(memberDto, mockBindingResult, model));
    }

    @Test
    void processSignUpFormWithValidationSuccess() {
        when(memberService.findByUsername(any())).thenReturn(null);
        when(memberService.findByEmail(any())).thenReturn(null);
        doReturn(false).when(mockBindingResult).hasErrors();
        assertEquals("index", controller.processSignUpForm(memberDto, mockBindingResult, model));
    }

    @Test
    void processSignUpFormWithDuplicateUsername() {
        when(memberService.findByUsername(any())).thenReturn(new Member());
        when(memberService.findByEmail(any())).thenReturn(null);
        doReturn(false).when(mockBindingResult).hasErrors();
        assertEquals("security/sign-up", controller.processSignUpForm(memberDto, mockBindingResult, model));
    }

    @Test
    void processSignUpFormWithDuplicateEmail() {
        when(memberService.findByUsername(any())).thenReturn(null);
        when(memberService.findByEmail(any())).thenReturn(new Member());
        doReturn(false).when(mockBindingResult).hasErrors();
        assertEquals("security/sign-up", controller.processSignUpForm(memberDto, mockBindingResult, model));
    }

    @Test
    void signInForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}