package com.financescript.springapp.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TwitterDtoTest {

    TwitterDto tweet1;
    TwitterDto tweet2;
    TwitterDto tweet3;

    @BeforeEach
    void setUp() {
        tweet1 = new TwitterDto("AAPL");
        tweet1.setMessage("reports...");

        tweet2 = new TwitterDto("AAPL"); // same as tweet1
        tweet2.setMessage("reports...XYZ");  // different from tweet1

        tweet3 = new TwitterDto("XYZ");
        tweet3.setMessage("reports...");
    }

    @Test
    void testEquals() {
        assertEquals(tweet1, tweet2);
        assertNotEquals(tweet1, tweet3);
    }

    @Test
    void testHashCode() {
        Set<TwitterDto> tweets = new HashSet<>();
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        assertEquals(2, tweets.size());
    }

    @Test
    void testToString() {
        assertEquals("TwitterDto(\"AAPL\", \"reports...\")", tweet1.toString());
    }
}