package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.Role;
import com.financescript.springapp.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class MemberDtoToMemberTest {
    public static final String USER_NAME = "test_user1";
    public static final String PASSWORD = "test_pass1";
    public static final String EMAIL = "tEst1@financescript.com";

    MemberDtoToMember converter;

    @Mock
    RoleService roleService;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new MemberDtoToMember(roleService);
    }

    @Test
    public void convert() {
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(USER_NAME);
        memberDto.setPassword(PASSWORD);
        memberDto.setEmail(EMAIL);
        Member member = converter.convert(memberDto);
        assertNotEquals(PASSWORD, member.getPassword()); // check if the password is encrypted
        assertEquals("TEST_USER1", member.getUsername());
        assertEquals("test1@financescript.com", member.getEmail());
        assertNotNull(member.getArticles());
        assertNotNull(member.getSubComments());
        assertNotNull(member.getComments());
    }
}