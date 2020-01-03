package com.financescript.springapp.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    Member member1;

    @BeforeEach
    void setUp() {
        member1 = new Member();
    }

    @Test
    void addRole() {
        int numRole = member1.getRoles().size();
        Role role = new Role("ROLE_DUMMY");
        member1.addRole(role);
        assertEquals(numRole + 1, member1.getRoles().size());
    }
}