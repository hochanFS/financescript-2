package com.financescript.springapp.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectorDataDownloaderTest {

    SectorDataDownloader sectorDataDownloader;

    @BeforeEach
    void setUp() {
        sectorDataDownloader = new SectorDataDownloader();
    }

    @Test
    void getData() {
        assertDoesNotThrow(() -> sectorDataDownloader.getData());
        for (String s : sectorDataDownloader.getCurrentSectors().getRealTimeReturn().toList()) {
            assertEquals('%', s.charAt(s.length() - 1));
        }
        for (String s : sectorDataDownloader.getCurrentSectors().getYoyReturn().toList()) {
            assertEquals('%', s.charAt(s.length() - 1));
        }
    }
}