package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.repositories.MemberRepository;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MemberJpaServiceTest {

    @Mock
    MemberRepository memberRepository;

    MemberService memberService;

    private final Long USER1_ID = 1L;
    private Member member1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<Member> members = new ArrayList<>();
        memberService = new MemberJpaService(memberRepository);
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
}