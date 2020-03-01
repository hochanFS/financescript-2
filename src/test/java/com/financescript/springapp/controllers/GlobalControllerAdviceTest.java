package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.MemberDto;
import com.financescript.springapp.dto.RankValueDto;
import com.financescript.springapp.dto.SectorDto;
import com.financescript.springapp.rest.EarningsDataDownloader;
import com.financescript.springapp.rest.SectorDataDownloader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GlobalControllerAdviceTest {
    GlobalControllerAdvice globalControllerAdvice;

    @Mock
    EarningsDataDownloader earningsDataDownloader;

    @Mock
    SectorDataDownloader sectorDataDownloader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        globalControllerAdvice = new GlobalControllerAdvice(sectorDataDownloader, earningsDataDownloader);
    }

    @Test
    void populateUser_activeShortPrincipal() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("USER1");

        assertEquals("USER1", globalControllerAdvice.populateUser(principal));
    }

    @Test
    void populateUser_activeLongPrincipal() {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("USER1234567890123456");

        assertNull(globalControllerAdvice.populateUser(principal));
    }

    @Test
    void populateUser_nullPrincipal() {
        Principal principal = null;

        assertNull(globalControllerAdvice.populateUser(principal));
    }

    @Test
    void test_sectorTitles() {
        String[] solutions =
                {"Discretionary", "Staples", "Energy",
                        "Financials", "Health Care", "Industrials",
                        "Minerals", "Technology", "Utilities"};
        String[] values = globalControllerAdvice.sectorTitles();
        for (int i = 0; i < 9; i++) {
            assertEquals(solutions[i], values[i]);
        }
    }

    @Test
    public void sectorReturns() {
        SectorDto sectorDto = Mockito.mock(SectorDto.class);
        RankValueDto rankValueDto = Mockito.mock(RankValueDto.class);
        when(sectorDataDownloader.getCurrentSectors()).thenReturn(sectorDto);
        when(sectorDto.getRealTimeReturn()).thenReturn(rankValueDto);
        when(rankValueDto.toList()).thenReturn(null);
        globalControllerAdvice.sectorReturns();
        verify(sectorDataDownloader, times(1)).getCurrentSectors();
        verify(sectorDto, times(1)).getRealTimeReturn();
    }

    @Test
    public void earnings() {
        globalControllerAdvice.earnings();
        verify(earningsDataDownloader, times(1)).getEarningsSummary();
    }
}