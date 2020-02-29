package com.financescript.springapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TwitterDto {
    String symbol;
    String message;

    public TwitterDto(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwitterDto)) return false;
        TwitterDto that = (TwitterDto) o;
        return symbol.equals(that.symbol);
    }

    public TwitterDto(String symbol, String message) {
        this.symbol = symbol;
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return "new TwitterDto(\"" + symbol +
                "\", \"" + message +
                "\")";
    }
}
