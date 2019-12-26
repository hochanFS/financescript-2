package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Member;
import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class MemberToMemberDto implements Converter<Member, MemberDto> {
    @Synchronized
    @Nullable
    @Override
    public MemberDto convert(Member source) {
        final MemberDto memberDto = new MemberDto();
        memberDto.setId(source.getId());
        memberDto.setEmail(source.getEmail());
        memberDto.setCreated(source.getCreationTime());
        memberDto.setUsername(source.getUsername());
        memberDto.setArticles(source.getArticles());
        memberDto.setComments(source.getComments());
        memberDto.setSubComments(source.getSubComments());
        return memberDto;
    }
}
