package com.financescript.springapp.services;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.PasswordResetToken;
import com.financescript.springapp.repositories.PasswordTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PasswordTokenServiceImplTest {

    @Mock
    PasswordTokenRepository passwordTokenRepository;

    PasswordTokenService passwordTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordTokenService = new PasswordTokenServiceImpl(passwordTokenRepository);
    }

    @Test
    void validatePasswordToken__invalidToken() {
        when(passwordTokenRepository.findByToken(anyString())).thenReturn(null);
        String s = passwordTokenService.validatePasswordToken(1L, "abcde");
        assertEquals("The token is invalid.", s);
    }

    @Test
    void validatePasswordToken__invalidToken2() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        Member member = new Member();
        member.setId(2L);
        passwordResetToken.setUser(member);
        when(passwordTokenRepository.findByToken(anyString())).thenReturn(passwordResetToken);
        String s = passwordTokenService.validatePasswordToken(1L, "abcde");
        assertEquals("The token is invalid.", s);
    }

    @Test
    void validatePasswordToken__expiredToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        Member member = new Member();
        member.setId(1L);
        passwordResetToken.setUser(member);
        passwordResetToken.setExpiryDate(LocalDateTime.now().minusMinutes(100000));
        when(passwordTokenRepository.findByToken(anyString())).thenReturn(passwordResetToken);
        String s = passwordTokenService.validatePasswordToken(1L, "abcde");
        assertEquals("The token has expired.", s);
    }

    @Test
    void validatePasswordToken__validToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        Member member = new Member();
        member.setId(1L);
        passwordResetToken.setUser(member);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(100));
        when(passwordTokenRepository.findByToken(anyString())).thenReturn(passwordResetToken);
        String s = passwordTokenService.validatePasswordToken(1L, "abcde");
        assertNull(s);
    }
}