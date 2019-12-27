package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUsername(String username);
}
