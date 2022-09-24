package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExchangerateAvailableCurrenciesResponseFixture {

    public static ResponseEntity<ExchangerateAvailableCurrenciesResponse> get200() {

        ExchangerateAvailableCurrenciesResponse response = ExchangerateAvailableCurrenciesResponse.builder()
                .success(true)
                .symbol("PLN", new ExchangerateAvailableCurrenciesResponse.Symbol("Polish Zloty", "PLN"))
                .symbol("EUR", new ExchangerateAvailableCurrenciesResponse.Symbol("Euro", "EUR"))
                .build();

        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ExchangerateAvailableCurrenciesResponse> get500() {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public static ResponseEntity<ExchangerateAvailableCurrenciesResponse> emptyBody() {

        return ResponseEntity.ok(null);
    }

}
