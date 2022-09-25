package com.awin.currencyconverter.api.exchange;

import com.awin.currencyconverter.domain.exchange.Currencies;
import com.awin.currencyconverter.domain.exchange.CurrencyConversion;
import com.awin.currencyconverter.domain.exchange.MultiCurrencyConversion;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MultiCurrencyConversionResponse {

    private String source;
    private double amount;
    private Map<String,Double> rates;
    private Map<String,Double> converted;

    public static MultiCurrencyConversionResponse from(MultiCurrencyConversion multiCurrencyConversion) {
        return MultiCurrencyConversionResponse.builder()
                .source(multiCurrencyConversion.getSource())
                .amount(multiCurrencyConversion.getAmount())
                .rates(multiCurrencyConversion.getRates())
                .converted(multiCurrencyConversion.getConversions())
                .build();

    }
}
