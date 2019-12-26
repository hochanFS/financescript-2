package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.repositories.MemberRepository;
import com.financescript.springapp.services.MemberService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberJpaService implements MemberService {
    private final MemberRepository memberRepository;

    public MemberJpaService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
}
