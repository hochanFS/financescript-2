package com.financescript.springapp.services;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.dto.PasswordDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {
    List<Member> findAll();
    void delete(Member object);
    Member save(Member member);
    void deleteById(Long id);
    Member save(MemberDto memberDto);
    Member findByUsername(String username);
    Member findByEmail(String email);
    void createPasswordResetTokenForUser(Member user, String token);
    Member changePassword(String username, PasswordDto passwordDto);
}
