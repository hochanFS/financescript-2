package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.services.EmailService;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
    private EmailService emailService;

    @Mock
    private Model model;

    MemberDto memberDto;

    SecurityController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        controller = new SecurityController(memberService, emailService);
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

    @Test
    void goToForgotPassword() throws Exception {
        mockMvc.perform(get("/forgotPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/forgotPassword"));
    }

    @Test
    void resetPassword__emptyMail() throws Exception {
        String s;
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/resetPassword")
                .param("email", ""); // testing "email" == null

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("success", "false"))
                .andExpect(model().attributeDoesNotExist("noUserFoundError"))
                .andExpect(view().name("security/forgotPassword"));
    }

    @Test
    void resetPassword__unknownEmail() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/resetPassword")
                .param("email", "someEmail@gmail.com");

        // when
        when(memberService.findByEmail(any())).thenReturn(null);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("success", "false"))
                .andExpect(model().attributeExists("noUserFoundError"))
                .andExpect(view().name("security/forgotPassword"));
    }

    @Test
    void resetPassword__knownEmail() throws Exception {
        // given
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/resetPassword")
                .param("email", "nyhochan.lee@gmail.com");
        Member member = new Member();
        member.setUsername("user1");
        member.setEmail("nyhochan.lee@gmail.com");
        member.setId(1L);

        // when
        when(memberService.findByEmail(any())).thenReturn(member);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("noUserFoundError"))
                .andExpect(model().attribute("success", "true"))
                .andExpect(view().name("security/forgotPassword"));
        verify(memberService, times(1)).createPasswordResetTokenForUser(any(Member.class), anyString());
        verify(emailService, times(1)).sendSimpleMessage(anyString(), anyString(), anyString());
    }
}