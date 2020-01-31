package com.financescript.springapp.services;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.PasswordResetToken;
import com.financescript.springapp.repositories.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;


@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public PasswordTokenServiceImpl(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    public String validatePasswordToken(Long id, String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        if (passwordResetToken == null || ! passwordResetToken.getUser().getId().equals(id)) {
            return "The token is invalid.";
        }
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(passwordResetToken.getExpiryDate())) {
            return "The token has expired.";
        }
        Member user = passwordResetToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
