package com.awin.currencyconverter.domain.exchange;

import lombok.*;

import java.util.List;


public class CurrencyExchangeServiceRequests {

    private CurrencyExchangeServiceRequests() {
    }

    @Getter
    @Builder
    public static class CurrencyConversionDetails{
        private String target;
        private String source;
        private double amount;
    }

    @Getter
    @Builder
    public static class MultiCurrencyConversionDetails{
        @Singular
        private List<String> targets;
        private String source;
        private double amount;

    }



}
