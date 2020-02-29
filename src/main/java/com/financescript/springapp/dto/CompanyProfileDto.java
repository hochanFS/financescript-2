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
public class CompanyProfileDto {
    @JsonProperty("mktCap")
    String marketCap;
    @JsonProperty("companyName")
    String companyName;
    @JsonProperty("website")
    String website;
    @JsonProperty("image")
    String image;
    @JsonProperty("changesPercentage")
    String changesPercentage;
}
