package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import org.springframework.http.ResponseEntity;

public class ExchangerateAvailableCurrenciesResponseFixture {

    public static ResponseEntity<ExchangerateAvailableCurrenciesResponse> get() {

        ExchangerateAvailableCurrenciesResponse response = ExchangerateAvailableCurrenciesResponse.builder()
                .success(true)
                .symbol("PLN", new ExchangerateAvailableCurrenciesResponse.Symbol("Polish Zloty", "PLN"))
                .symbol("EUR", new ExchangerateAvailableCurrenciesResponse.Symbol("Euro", "EUR"))
                .build();

        return ResponseEntity.ok(response);
    }

}
