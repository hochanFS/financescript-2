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
public class RankValueDto {
    @JsonProperty("Utilities")
    private String utilities;

    @JsonProperty("Real Estate")
    private String realEstate;

    @JsonProperty("Information Technology")
    private String technology;

    @JsonProperty("Consumer Discretionary")
    private String discretionary;

    @JsonProperty("Consumer Staples")
    private String staples;

    @JsonProperty("Industrials")
    private String industrials;

    @JsonProperty("Financials")
    private String financials;

    @JsonProperty("Materials")
    private String materials;

    @JsonProperty("Energy")
    private String energy;

    public RankValueDto(String utilities, String realEstate, String technology, String discretionary, String staples, String industrials, String financials, String materials, String energy) {
        this.utilities = utilities;
        this.realEstate = realEstate;
        this.technology = technology;
        this.discretionary = discretionary;
        this.staples = staples;
        this.industrials = industrials;
        this.financials = financials;
        this.materials = materials;
        this.energy = energy;
    }

    public String[] toList() {
        String[] stringArray = {
                this.utilities, this.realEstate, this.technology, this.discretionary, this.staples,
                this.industrials, this.financials, this.materials, this.energy,
        };
        return stringArray;
    }
}
