package com.financescript.springapp.domains.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeWriterTest {

    LocalDateTime dateTime;
    LocalDateTimeWriter writer;

    @BeforeEach
    void setUp() {
        writer = new LocalDateTimeWriter();
        dateTime = LocalDateTime.of(2019, 6, 3, 8, 30, 29);
    }

    @Test
    void write() {
        assertEquals("06/03/19 8:30", writer.write(dateTime));
    }

    @Test
    void writeDate() {
        assertEquals("06/03/19", writer.writeDate(dateTime));
    }
}