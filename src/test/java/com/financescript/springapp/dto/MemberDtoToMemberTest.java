package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberDtoToMemberTest {
    public static final String USER_NAME = "test_user1";
    public static final String PASSWORD = "test_pass1";
    public static final String EMAIL = "test1@financescript.com";

    MemberDtoToMember converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new MemberDtoToMember();
    }

    @Test
    public void convert() {
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(USER_NAME);
        memberDto.setPassword(PASSWORD);
        memberDto.setEmail(EMAIL);
        Member member = converter.convert(memberDto);
        assertEquals(PASSWORD, member.getPassword());
        assertEquals(USER_NAME, member.getUsername());
        assertEquals(EMAIL, member.getEmail());
        assertNotNull(member.getArticles());
        assertNotNull(member.getSubComments());
        assertNotNull(member.getComments());
    }
}