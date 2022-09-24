package com.awin.currencyconverter.client.exchangerate.responses;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ExchangerateAvailableCurrenciesResponse {

    private boolean success;

    @Singular
    private Map<String,Symbol> symbols;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Symbol{

        private String description;
        private String code;

    }

}
