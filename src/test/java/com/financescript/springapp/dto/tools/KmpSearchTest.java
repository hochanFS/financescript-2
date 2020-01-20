package com.financescript.springapp.dto.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KmpSearchTest {

    KmpSearch kmpSearch;

    @BeforeEach
    void setUp() {
        kmpSearch = new KmpSearch();
    }

    @Test
    void substringSearch() {
        String txt1 = "ABABDABACDABABCABAB";
        String pat1 = "ABABCABAB";
        List<Integer> solutions1 = kmpSearch.substringSearch(pat1, txt1);
        assertEquals(10, solutions1.get(0));
    }
}