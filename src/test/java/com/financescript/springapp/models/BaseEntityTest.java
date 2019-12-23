package com.financescript.springapp.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BaseEntityTest {

    private BaseEntity be1;
    private BaseEntity be2;
    private final Long be1Id = 1L;

    @BeforeEach
    void setUp() {
        be1 = new BaseEntity();
        be2 = new BaseEntity();
        be1.setId(be1Id);
    }

    @Test
    void isNew() {
        assertTrue(be2.isNew());
        assertFalse(be1.isNew());
    }

    @Test
    void getId() {
        assertEquals(be1Id, be1.getId());
    }
}