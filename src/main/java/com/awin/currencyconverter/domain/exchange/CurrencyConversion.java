package com.awin.currencyconverter.domain.exchange;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class CurrencyConversion {

    @NonNull private String target;
    @NonNull private String source;
    @NonNull private double amount;
    @NonNull private double rate;
    @NonNull private double converted;
}
