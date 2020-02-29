package com.financescript.springapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financescript.springapp.dto.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Getter
@Setter
public class EarningsDataDownloader {
    private final String MARKET_STATUS_URL = "https://financialmodelingprep.com/api/v3/is-the-market-open";
    private final String PREFIX_URL = "https://financialmodelingprep.com/api/v3/company/profile/";
    private final int MAX_SIZE = 10;

    private ArrayDeque<String> earningsSummary;
    private TwitterDataDownloader twitterDataDownloader;
    private Set<TwitterDto> currentSymbolSet;
    private Set<String> symbolsInEarningsSummary;

    @Autowired
    public EarningsDataDownloader(TwitterDataDownloader twitterDataDownloader) {
        this.twitterDataDownloader = twitterDataDownloader;
        this.twitterDataDownloader.updateWatchList();
        this.earningsSummary = new ArrayDeque<>();
        this.currentSymbolSet = new HashSet<>();
        this.symbolsInEarningsSummary = new HashSet<>();
    }

    @Scheduled(cron="0 0/5 8,16,17 * * MON-FRI", zone="EST")
    @Synchronized
    public void fillEarningsSummary() {
        List<TwitterDto> symbols = twitterDataDownloader.getWatchList();

        if (!symbols.isEmpty()) {
            for (int i = symbols.size() - 1; i >= 0; i--) {
                TwitterDto symbol = symbols.get(i);
                if (!currentSymbolSet.contains(symbol)) {
                    String notification = constructNotificationMessage(symbol);
                    if (notification != null) {
                        if (earningsSummary.size() > MAX_SIZE) {
                            earningsSummary.removeLast(); // how to remove from currentSymbolSet?
                        }
                        symbolsInEarningsSummary.add(symbol.getSymbol());
                        earningsSummary.addFirst(notification);
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (Exception exc) {
                    log.error("Interrupted while downloading earnings data");
                }
            }
        }
    }

    @Scheduled(cron="0 0 13 ? 6 Sat", zone="EST")
    @Synchronized
    public void resetSymbolSet() {
        this.currentSymbolSet = new HashSet<>();
    }

    public String constructNotificationMessage(TwitterDto tweet) {
        String address = PREFIX_URL + tweet.getSymbol();
//        System.out.println("Accessing " + address);
        try {
            // not checking NullPointException since it will be caught by catch statement.
            URL url = new URL(address);
            ObjectMapper mapper = new ObjectMapper();
            CompanyInfoDto companyInfoDto = mapper.readValue(url, CompanyInfoDto.class);
            CompanyProfileDto companyProfileDto = companyInfoDto.getProfile();
            double mktCap = Double.parseDouble(companyProfileDto.getMarketCap());
            if (mktCap < 1e9) {
                // assumes < 1B mkt cap companies are not familiar and not interesting to the users
                return null;
            }

            StringBuilder sb = new StringBuilder();

            String website = companyProfileDto.getWebsite();
            String img = companyProfileDto.getImage();

            sb.append("<a href=\"").append(website).append("\" target=\"_blank\" ")
                    .append(" class=\"feed--a\"").append(">").append(tweet.getSymbol());
            sb.append("</a>").append(" (").append(companyProfileDto.getCompanyName()).append(") ").append(tweet.getMessage());

            currentSymbolSet.add(tweet); // prevents repeated call
            return sb.toString();
        } catch(Exception exc) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        TwitterDataDownloader twitterDataDownloader = new TwitterDataDownloader();
//        EarningsDataDownloader earningsDataDownloader = new EarningsDataDownloader(twitterDataDownloader);
//        earningsDataDownloader.fillEarningsSummary();
//
//        for (String s : earningsDataDownloader.getEarningsSummary()) {
//            System.out.println(s);
//        }
//    }

}
