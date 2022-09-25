package com.awin.currencyconverter.domain.exchange;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class CurrencyConversion {

    @NonNull private String target;
    @NonNull private String source;
    private double amount;
    private double rate;
    private double converted;
}
