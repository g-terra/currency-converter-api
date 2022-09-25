package com.awin.currencyconverter.api.exchange;

import com.awin.currencyconverter.domain.exchange.Currencies;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CurrenciesResponse {

    List<CurrencyResponse> availableCurrencies;

    @Getter
    @Builder
    public static class CurrencyResponse {
        @NonNull
        private String code;
        @NonNull
        private String description;
        
        public static CurrencyResponse from(Currencies.Currency currency){
            
            return CurrencyResponse.builder()
                    .code(currency.getCode())
                    .description(currency.getDescription())
                    .build();
            
        }
    }

    public static CurrenciesResponse from(Currencies currencies) {

        return CurrenciesResponse.builder()
                .availableCurrencies(mapToResponse(currencies))
                .build();

    }

    private static List<CurrencyResponse> mapToResponse(Currencies currencies) {
        return currencies.getAvailableCurrencies()
                .stream()
                .map(CurrencyResponse::from)
                .collect(Collectors.toList());
    }
}
