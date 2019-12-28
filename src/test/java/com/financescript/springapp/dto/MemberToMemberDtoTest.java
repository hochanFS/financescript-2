package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberToMemberDtoTest {

    public static final String USER_NAME = "test_user1";
    public static final String PASSWORD = "test_pass1";
    public static final String EMAIL = "Test1@financescript.com";

    MemberToMemberDto converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new MemberToMemberDto();
    }

    @Test
    public void convert() {
        Member member = new Member();
        member.setUsername(USER_NAME);
        member.setPassword(PASSWORD);
        member.setEmail(EMAIL);
        MemberDto memberDto = converter.convert(member);
        assertEquals("TEST_USER1", memberDto.getUsername());
        assertEquals("test1@financescript.com", memberDto.getEmail());
        assertNotNull(memberDto.getArticles());
        assertNotNull(memberDto.getSubComments());
        assertNotNull(memberDto.getComments());
        assertNull(memberDto.getPassword());
    }
}