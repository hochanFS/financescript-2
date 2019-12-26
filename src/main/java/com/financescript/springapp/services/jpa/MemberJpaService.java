package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.dto.MemberDtoToMember;
import com.financescript.springapp.dto.MemberToMemberDto;
import com.financescript.springapp.repositories.MemberRepository;
import com.financescript.springapp.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberJpaService implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberDtoToMember memberDtoToMember;
    private final MemberToMemberDto memberToMemberDto;

    public MemberJpaService(MemberRepository memberRepository, MemberDtoToMember memberDtoToMember, MemberToMemberDto memberToMemberDto) {
        this.memberRepository = memberRepository;
        this.memberDtoToMember = memberDtoToMember;
        this.memberToMemberDto = memberToMemberDto;
    }

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(members::add);
        return members;
    }

    @Override
    @Transactional
    public void delete(Member object) {
        memberRepository.delete(object);
    }

    @Override
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Member save(MemberDto memberDto) {
        Member member = memberDtoToMember.convert(memberDto);
        if (member != null)
            memberRepository.save(member);
        else
            log.warn("Null object accepted to MemberJpaService::save");
        return member;
    }
}
