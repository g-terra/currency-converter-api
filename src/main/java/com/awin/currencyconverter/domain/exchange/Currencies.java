package com.awin.currencyconverter.domain.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Currencies {

    List<Currency> availableCurrencies;

    @Getter
    @AllArgsConstructor
    public static class Currency {
        private String code;
        private String description;
    }


}
