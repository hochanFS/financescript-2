package com.financescript.springapp.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwitterDataDownloaderTest {

    TwitterDataDownloader twitterDataDownloader;

    @BeforeEach
    void setUp() {
        twitterDataDownloader = new TwitterDataDownloader();
    }

    @Test
    void updateWatchList() {
        // requires API key for testing, which needs to be set.. Need more research on how to do that in CircleCI
        // For the time being, this is replaced with main method inside the class
    }
}