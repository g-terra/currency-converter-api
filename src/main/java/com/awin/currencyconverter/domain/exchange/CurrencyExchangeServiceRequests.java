package com.awin.currencyconverter.domain.exchange;

import lombok.Builder;
import lombok.Getter;

public class CurrencyExchangeServiceRequests {

    @Getter
    @Builder
    public static class CurrencyConversionDetails{
        private String target;
        private String source;
        private double amount;

    }


}
