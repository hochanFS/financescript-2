package com.financescript.springapp.controllers;

import com.financescript.springapp.rest.EarningsDataDownloader;
import com.financescript.springapp.rest.SectorDataDownloader;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    SectorDataDownloader sectorDataDownloader;
    EarningsDataDownloader earningsDataDownloader;

    public GlobalControllerAdvice(SectorDataDownloader sectorDataDownloader, EarningsDataDownloader earningsDataDownloader) {
        this.sectorDataDownloader = sectorDataDownloader;
        this.earningsDataDownloader = earningsDataDownloader;
    }

    private final static String[] SECTOR_TITLES =
            {"Discretionary", "Staples", "Energy",
                    "Financials", "Health Care", "Industrials",
                    "Minerals", "Technology", "Utilities"};

    @ModelAttribute("sessionUsername")
    public String populateUser(Principal principal) {
        String sessionUsername = null;
        if (principal != null && principal.getName().length() <= 15)
            sessionUsername = principal.getName();
        return sessionUsername;
    }

    @ModelAttribute("sectorTitles")
    public String[] sectorTitles() {
        return SECTOR_TITLES;
    }

    @ModelAttribute("sectorReturns")
    public String[] sectorReturns() {
        return sectorDataDownloader.getCurrentSectors().getRealTimeReturn().toList();
    }

    @ModelAttribute("earningReports")
    public List<String> earnings() {
        return earningsDataDownloader.getEarningsSummary();
    }
}
