package com.financescript.springapp.domains.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeWriter {
    public String write(LocalDateTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        StringBuilder sb = new StringBuilder();
        sb.append(writeDate(time))
                .append(' ').append(hour).append(':').append(minute);
        return sb.toString();
    }

    public String writeDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YY");
        return time.format(formatter);
    }
}
