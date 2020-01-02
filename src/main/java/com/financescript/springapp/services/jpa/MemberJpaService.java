package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.Role;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.dto.MemberDtoToMember;
import com.financescript.springapp.dto.MemberToMemberDto;
import com.financescript.springapp.repositories.MemberRepository;
import com.financescript.springapp.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        if (member != null) {
            memberRepository.save(member);
        }
        else
            log.warn("Null object passed to MemberJpaService::save");
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public Member findByUsername(String username) {
        username = username.toUpperCase();
        return memberRepository.findByUsername(username);
    }

    @Override
    public Member findByEmail(String email) {
        String[] divided = email.split("@");
        if (divided.length != 2) {
            return null;
        }
        email = divided[0].toLowerCase() + "@" + divided[1];
        return memberRepository.findByEmail(email);
    }
}
