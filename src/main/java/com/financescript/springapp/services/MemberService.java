package com.financescript.springapp.services;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;

import java.util.List;

public interface MemberService {
    List<Member> findAll();
    void delete(Member object);
    Member save(Member member);
    void deleteById(Long id);
    Member save(MemberDto memberDto);
}
