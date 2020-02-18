package com.financescript.springapp.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankValueDtoTest {

    RankValueDto rankValueDto;
    List<String> randomValues = List.of("300", "200", "1.00%", "0.50%", "300", "300", "500", "300", "400");

    @BeforeEach
    void setUp() {
        rankValueDto = new RankValueDto("300", "200", "1.00%", "0.50%", "300", "300", "500", "300", "400");
    }

    @Test
    void toList() {
        String[] toList = rankValueDto.toList();
        for (int i = 0; i < randomValues.size(); i++) {
            assertEquals(randomValues.get(i), toList[i]);
        }
    }
}