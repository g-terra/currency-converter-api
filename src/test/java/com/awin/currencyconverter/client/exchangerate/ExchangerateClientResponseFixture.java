package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateRateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ExchangerateClientResponseFixture {

    public static ResponseEntity<ExchangerateRateResponse> rateWithStatus200(Double expectedRate) {

        ExchangerateRateResponse fixture = ExchangerateRateResponse.builder()
                .base("EUR")
                .success(true)
                .rates(Map.of("PLN", 2d))
                .build();

        return ResponseEntity.ok(fixture);

    }

    public static ResponseEntity<ExchangerateRateResponse> rateWithStatus404() {

        ExchangerateRateResponse fixture = ExchangerateRateResponse.builder()
                .success(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fixture);

    }


    public static ResponseEntity<ExchangerateAvailableCurrenciesResponse> availableCurrencies200() {

        ExchangerateAvailableCurrenciesResponse response = ExchangerateAvailableCurrenciesResponse.builder()
                .success(true)
                .symbol("PLN", new ExchangerateAvailableCurrenciesResponse.Symbol("Polish Zloty", "PLN"))
                .symbol("EUR", new ExchangerateAvailableCurrenciesResponse.Symbol("Euro", "EUR"))
                .build();

        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ExchangerateAvailableCurrenciesResponse> availableCurrencies404() {

        ExchangerateAvailableCurrenciesResponse fixture = ExchangerateAvailableCurrenciesResponse.builder()
                .success(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fixture);
    }


}
