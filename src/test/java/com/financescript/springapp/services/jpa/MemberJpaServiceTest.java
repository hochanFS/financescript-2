package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.PasswordResetToken;
import com.financescript.springapp.domains.Role;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.dto.MemberDtoToMember;
import com.financescript.springapp.dto.MemberToMemberDto;
import com.financescript.springapp.dto.PasswordDto;
import com.financescript.springapp.repositories.MemberRepository;
import com.financescript.springapp.repositories.PasswordTokenRepository;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MemberJpaServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    MemberToMemberDto memberToMemberDto;

    @Mock
    MemberDtoToMember memberDtoToMember;

    @Mock
    PasswordTokenRepository passwordTokenRepository;

    MemberService memberService;

    private final Long USER1_ID = 1L;
    private Member member1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<Member> members = new ArrayList<>();
        memberService = new MemberJpaService(memberRepository, memberDtoToMember, memberToMemberDto, passwordTokenRepository);
        member1 = new Member();
        member1.setId(USER1_ID);
        members.add(member1);
        when(memberRepository.findAll()).thenReturn(members);
    }

    @Test
    void save() {
        Member member2 = new Member();
        member2.setId(2L);
        memberService.save(member2);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void delete() {
        memberService.delete(member1);
        verify(memberRepository, times(1)).delete(any(Member.class));
    }

    @Test
    void deleteById() {
        memberService.deleteById(USER1_ID);
        verify(memberRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveMemberDto() {
        MemberDto memberDto = new MemberDto();
        when(memberDtoToMember.convert(any(MemberDto.class))).thenReturn(member1);
        memberService.save(memberDto);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void loadUserByUserNameNull() {
        when(memberRepository.findByUsername(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> memberService.loadUserByUsername("user1"));
    }

    @Test
    void loadUserByUserNameNotNull() {
        Member member = new Member();
        member.addRole(new Role("MEMBER"));
        member.setUsername("member1");
        member.setPassword("temporalPassword1");
        member.setId(1L);
        when(memberRepository.findByUsername(anyString())).thenReturn(member);
        assertNotNull(memberService.loadUserByUsername("user1"));
        verify(memberRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void findAllTest() {
        List<Member> members = new ArrayList<>();
        members.add(new Member());
        when(memberRepository.findAll()).thenReturn(members);
        memberService.findAll();
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    public void findByUsername() {
        when(memberRepository.findByUsername(anyString())).thenReturn(member1);
        memberService.findByUsername("member1");
        verify(memberRepository, times(1)).findByUsername("MEMBER1");
    }

    @Test
    public void findByEmail() {
        when(memberRepository.findByEmail(anyString())).thenReturn(member1);
        memberService.findByEmail("Test1@financescript.com");
        verify(memberRepository, times(1)).findByEmail("test1@financescript.com");
    }

    @Test
    public void createPasswordResetTokenForUser() {
        memberService.createPasswordResetTokenForUser(new Member(), "abcdef");
        verify(passwordTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    public void changePassword__availableMember() {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setPassword("random_password");
        Member temp = new Member();
        when(memberService.findByUsername("USER1")).thenReturn(temp);

        Member member = memberService.changePassword("USER1", passwordDto);
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void changePassword__unavailableMember() {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setPassword("random_password");
        Member temp = new Member();
        when(memberService.findByUsername("USER1")).thenReturn(null);

        Member member = memberService.changePassword("USER1", passwordDto);
        verify(memberRepository, times(1)).findByUsername(anyString());
        verify(memberRepository, times(0)).save(any(Member.class));
    }
}