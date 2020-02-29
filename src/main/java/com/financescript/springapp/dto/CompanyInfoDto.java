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
public class CompanyInfoDto {
    @JsonProperty("symbol")
    String symbol;
    @JsonProperty("profile")
    CompanyProfileDto profile;
}
