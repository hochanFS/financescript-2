package com.financescript.springapp.rest;

import com.financescript.springapp.dto.TwitterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EarningsDataDownloaderTest {

    EarningsDataDownloader earningsDataDownloader;

    @Mock
    TwitterDataDownloader twitterDataDownloader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<TwitterDto> tweets = new ArrayList<>(
                List.of(new TwitterDto("CKH", "reports FQ4 earnings of -10c EPS and $192.76M Revs"),
                        new TwitterDto("CPE", "reports FQ4 earnings of 23c EPS and $192.74M Revs"),
                        new TwitterDto("GLYC", "reports FQ4 earnings of -34c EPS and $0M Revs"),
                        new TwitterDto("VGR", "reports FQ4 earnings of 11c EPS and $439.57M Revs"),
                        new TwitterDto("SSP", "reports FQ4 earnings of 13c EPS and $444.40M Revs"),
                        new TwitterDto("SPR", "reports FQ4 earnings of 79c EPS and $1.96B Revs"),
                        new TwitterDto("ROCK", "reports FQ4 earnings of 62c EPS and $258.13M Revs"),
                        new TwitterDto("SGMO", "reports FQ4 earnings of 4c EPS and $54.85M Revs"),
                        new TwitterDto("W", "reports FQ4 earnings of $-2.80 EPS and $2.53B Revs"),
                        new TwitterDto("STRA", "reports FQ4 earnings of $2.13 EPS and $263.77M Revs"),
                        new TwitterDto("CKH", "reports FQ4 earnings of -10c EPS and $192.76M Revs"),
                        new TwitterDto("CPE", "reports FQ4 earnings of 23c EPS and $192.74M Revs"),
                        new TwitterDto("GLYC", "reports FQ4 earnings of -34c EPS and $0M Revs"),
                        new TwitterDto("VGR", "reports FQ4 earnings of 11c EPS and $439.57M Revs"),
                        new TwitterDto("SSP", "reports FQ4 earnings of 13c EPS and $444.40M Revs"),
                        new TwitterDto("SPR", "reports FQ4 earnings of 79c EPS and $1.96B Revs"),
                        new TwitterDto("ROCK", "reports FQ4 earnings of 62c EPS and $258.13M Revs"),
                        new TwitterDto("SGMO", "reports FQ4 earnings of 4c EPS and $54.85M Revs"),
                        new TwitterDto("W", "reports FQ4 earnings of $-2.80 EPS and $2.53B Revs"),
                        new TwitterDto("STRA", "reports FQ4 earnings of $2.13 EPS and $263.77M Revs")

                )
        );

        when(twitterDataDownloader.getWatchList()).thenReturn(tweets);
        earningsDataDownloader = new EarningsDataDownloader(twitterDataDownloader);
    }

    @Test
    void fillEarningsSummary() {
        assertDoesNotThrow(() -> earningsDataDownloader.fillEarningsSummary());
        assertNotEquals(0, earningsDataDownloader.getEarningsSummary().size());
    }

    @Test
    void resetSymbolSet() {
        earningsDataDownloader.resetSymbolSet();
        assertEquals(0, earningsDataDownloader.getCurrentSymbolSet().size());
    }

    @Test
    void constructNotificationMessage__smallTicker() {
        // this test will stop working if GLYC grows its market cap to above 1B USD

        TwitterDto bigTickerTweet = new TwitterDto("GLYC", "reports FQ4 earnings");
        assertNull(earningsDataDownloader.constructNotificationMessage(bigTickerTweet));
    }

    @Test
    void constructNotificationMessage__bigTicker() {
        // this test will stop working if AAPL shrinks its market cap to below 1B USD

        TwitterDto bigTickerTweet = new TwitterDto("AAPL", "reports FQ4 earnings");
        assertEquals("<a href=\"http://www.apple.com\" target=\"_blank\"  class=\"feed--a\">AAPL</a> (Apple Inc.) reports FQ4 earnings",
                earningsDataDownloader.constructNotificationMessage(bigTickerTweet));
    }

    @Test
    void constructNotificationMessage__invalidTicker() {
        TwitterDto invalidTickerTweet = new TwitterDto("ASDJFKLAJSFJ",
                "reports FQ4 earnings of -10c EPS and $192.76M Revs");
        assertNull(earningsDataDownloader.constructNotificationMessage(invalidTickerTweet));

    }
}