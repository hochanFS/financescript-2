package com.financescript.springapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectorDto {

    @JsonProperty("Rank A: Real-Time Performance")
    private RankValueDto realTimeReturn;

    @JsonProperty("Rank G: 1 Year Performance")
    private RankValueDto yoyReturn;
}
