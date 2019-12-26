package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Member;
import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class MemberDtoToMember implements Converter<MemberDto, Member> {

    @Synchronized
    @Nullable
    @Override
    public Member convert(MemberDto source) {
        final Member member = new Member();
        // We do NOT set matchingPassword and created fields.
        // matchingPassword field does not exist in Member class
        // created field is PrePersist quantity that needs to be set only once.
        member.setId(source.getId());
        member.setUsername(source.getUsername());
        member.setArticles(source.getArticles());
        member.setComments(source.getComments());
        member.setSubComments(source.getSubComments());
        member.setPassword(source.getPassword());
        member.setEmail(source.getEmail());
        return member;
    }
}
