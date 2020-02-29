package com.financescript.springapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financescript.springapp.dto.RankValueDto;
import com.financescript.springapp.dto.SectorDto;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;


@Component
@Slf4j
@Getter
@Setter
public class SectorDataDownloader {

    private final String SECTOR_DATA_URL = "https://www.alphavantage.co/query?function=SECTOR&apikey=779W3TIU0AXFK698";
    // TODO: consider putting it as an environment variable

    private SectorDto previousSectors;
    private SectorDto currentSectors;

    public SectorDataDownloader() {
        RankValueDto initialDto1 = new RankValueDto("-200", "-200","-200","-200","-200","-200","-200","-200","-200");
        RankValueDto initialDto2 = new RankValueDto("-200", "-200","-200","-200","-200","-200","-200","-200","-200");
        this.previousSectors = new SectorDto();
        this.previousSectors.setRealTimeReturn(initialDto1);
        this.previousSectors.setRealTimeReturn(initialDto2);
        this.currentSectors = new SectorDto();
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)  // should execute every 5 minutes
    @Synchronized
    public void getData() {
        Date date = new Date();
        date.toInstant().atZone(ZoneId.of("America/New_York"));
        log.info("Downloading sector return market data @ " + date.toString());
        this.currentSectors = new SectorDto();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            URL url = new URL(SECTOR_DATA_URL);
            currentSectors = mapper.readValue(url, SectorDto.class);
            previousSectors.setRealTimeReturn(currentSectors.getRealTimeReturn());
            previousSectors.setYoyReturn(currentSectors.getYoyReturn());
        } catch(Exception exc) {
            currentSectors = previousSectors;
            exc.printStackTrace();
        }
    }
}
