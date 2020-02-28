package com.financescript.springapp.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.time.ZoneId;
import java.util.*;

@Component
@Getter
@Setter
@Slf4j
public class TwitterDataDownloader {
    private final String CONSUMER_API_KEY = Optional.ofNullable(System.getenv("TWITTER_API_KEY"))
            .orElse("ENVIRONMENT_VARIABLE_NOT_DEFINED");
    private final String SECRET_CONSUMER_API_KEY = Optional.ofNullable(System.getenv("TWITTER_SECRET_API_KEY"))
            .orElse("ENVIRONMENT_VARIABLE_NOT_DEFINED");
    private final String ACCESS_API_KEY = Optional.ofNullable(System.getenv("ACCESS_API_KEY"))
            .orElse("ENVIRONMENT_VARIABLE_NOT_DEFINED");
    private final String SECRET_ACCESS_API_KEY = Optional.ofNullable(System.getenv("SECRET_ACCESS_API_KEY"))
            .orElse("ENVIRONMENT_VARIABLE_NOT_DEFINED");
    private Set<String> watchList = new HashSet<>();

    @Scheduled(fixedRate=1000 * 60 * 60 * 12) // twice a day
    public void updateWatchList() {
        Date date = new Date();
        date.toInstant().atZone(ZoneId.of("America/New_York"));
        log.info("Getting Twitter feed @ " + date.toString());
        this.watchList = new HashSet<>();
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(false).setOAuthConsumerKey(CONSUMER_API_KEY)
                .setOAuthConsumerSecret(SECRET_CONSUMER_API_KEY)
                .setOAuthAccessToken(ACCESS_API_KEY)
                .setOAuthAccessTokenSecret(SECRET_ACCESS_API_KEY);
        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = tf.getInstance();

        try {
            Paging page = new Paging (1, 30);
            List<Status> statuses1 = twitter.getUserTimeline("Estimize", page);
            List<Status> statuses2 = twitter.getUserTimeline("newsfilterio");
            addToWatchList(statuses1);
            addToWatchList(statuses2);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void addToWatchList(List<Status> statuses) {
        for (Status status : statuses) {
            String text = status.getText();
            String[] texts = text.split("\\s+");
            for (String s : texts) {
                if (s != null && s.length() >= 2 && s.charAt(0) == '$' && s.charAt(1) >= 'A' && s.charAt(1) <= 'Z') {
                    this.watchList.add(s.substring(1));
                }
            }
        }
    }

//    public static void main(String[] args) {
//        TwitterDataDownloader twitterDataDownloader = new TwitterDataDownloader();
//        twitterDataDownloader.updateWatchList();
//        System.out.println(twitterDataDownloader.watchList);
//    }
}
