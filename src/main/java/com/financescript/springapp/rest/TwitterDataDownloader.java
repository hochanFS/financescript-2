package com.financescript.springapp.rest;

import com.financescript.springapp.dto.TwitterDto;
import com.financescript.springapp.dto.tools.KmpSearch;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
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
    private List<TwitterDto> watchList = new ArrayList<>();
    KmpSearch kmpSearch = new KmpSearch();

    @Scheduled(fixedRate=1000 * 60 * 60 * 12, initialDelay = 1000*60) // twice a day
    @Synchronized
    public void updateWatchList() {
        Date date = new Date();
        date.toInstant().atZone(ZoneId.of("America/New_York"));
        log.info("Getting Twitter feed @ " + date.toString());
        this.watchList = new ArrayList<>();
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(false).setOAuthConsumerKey(CONSUMER_API_KEY)
                .setOAuthConsumerSecret(SECRET_CONSUMER_API_KEY)
                .setOAuthAccessToken(ACCESS_API_KEY)
                .setOAuthAccessTokenSecret(SECRET_ACCESS_API_KEY);
        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = tf.getInstance();

        try {
            Paging p = new Paging();
            p.setCount(100);
            List<Status> statuses1 = twitter.getUserTimeline("Estimize", p);
            // List<Status> statuses2 = twitter.getUserTimeline("newsfilterio");
            addToWatchList(statuses1);
            // addToWatchList(statuses2);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void addToWatchList(List<Status> statuses) {
        for (Status status : statuses) {
            String text = status.getText();
            String[] texts = text.split("\\s+");
            String s = texts[0];
            if (s != null && s.length() >= 2 && s.charAt(0) == '$' && s.charAt(1) >= 'A' && s.charAt(1) <= 'Z') {
                TwitterDto dt = new TwitterDto(s.substring(1));
                List<Integer> estimizes = kmpSearch.substringSearch("Estimize", text);
                List<Integer> indices = kmpSearch.substringSearch("reports", text);
                List<Integer> url = kmpSearch.substringSearch(" - https://", text);
                if (indices.size() == 1 && url.size() == 1 && (estimizes.size() == 0 || estimizes.get(0) < url.get(0))) {
                    dt.setMessage(text.substring(indices.get(0), url.get(0)));
                    watchList.add(dt);
                }
            }
        }
    }

    public static void main(String[] args) {
        TwitterDataDownloader twitterDataDownloader = new TwitterDataDownloader();
        twitterDataDownloader.updateWatchList();
        System.out.println(twitterDataDownloader.watchList);
    }
}
