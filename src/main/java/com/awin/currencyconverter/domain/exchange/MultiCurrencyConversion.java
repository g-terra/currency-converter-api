package com.awin.currencyconverter.domain.exchange;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class MultiCurrencyConversion {

    @NonNull
    private List<String> targets;

    @NonNull
    private String source;

    private double amount;

    @NonNull
    private Map<String, Double> rates;

    @NonNull
    @Singular
    private Map<String, Double> conversions;

}
