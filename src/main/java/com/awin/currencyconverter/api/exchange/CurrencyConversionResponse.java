package com.awin.currencyconverter.api.exchange;

import com.awin.currencyconverter.domain.exchange.CurrencyConversion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CurrencyConversionResponse {

    private String target;
    private String source;
    private double amount;
    private double rate;
    private double converted;

    public static CurrencyConversionResponse from(CurrencyConversion currencyConversion) {
        return CurrencyConversionResponse.builder()
                .target(currencyConversion.getTarget())
                .source(currencyConversion.getSource())
                .amount(currencyConversion.getAmount())
                .rate(currencyConversion.getRate())
                .converted(currencyConversion.getConverted())
                .build();

    }

}
