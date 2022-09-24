package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exception.CurrencyNotAvailableException;
import com.awin.currencyconverter.client.exception.FailedToRetrieveAvailableCurrencies;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ExchangerateCurrencyExchangeProviderTest {

    private ExchangerateCurrencyExchangeProvider provider;

    private ExchangerateClient client;

    @BeforeEach
    void setUp() {
        this.client = Mockito.mock(ExchangerateClient.class);
        this.provider = new ExchangerateCurrencyExchangeProvider(client);
    }

    @Test
    void should_return_expected_rate_for_given_target_and_source() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";
        Double expectedRate = 2d;

        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get200());
        
        ExchangerateRateResponse mockResponse = ExchangerateRateResponse.builder()
                .base(source)
                .success(true)
                .rates(Map.of(target, expectedRate))
                .build();

        when(client.getRate(source, target)).thenReturn(ResponseEntity.of(Optional.of(mockResponse)));

        //WHEN
        double actualRate = provider.getRate(source, target);

        //THEN
        assertEquals(expectedRate, actualRate);

    }

    @Test
    void should_throw_exception_when_client_response_does_not_have_status_2xx() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";
        Double expectedRate = 2d;

        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get200());
        
        ExchangerateRateResponse mockResponse = ExchangerateRateResponse.builder()
                .base(source)
                .success(true)
                .rates(Map.of(target, expectedRate))
                .build();

        when(client.getRate(source, target)).thenReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(mockResponse));

        //WHEN
        FailedToRetrieveExchangeRateException ex = assertThrows(FailedToRetrieveExchangeRateException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve exchange rate for EUR -> PLN.", ex.getMessage());
        assertEquals("Provider server returned :503 SERVICE_UNAVAILABLE", ex.getReason());

    }


    @Test
    void should_throw_exception_when_client_response_is_empty() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        Map<String, Double> expectedRates = new HashMap<>();
        expectedRates.put(target,null);

        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get200());
        
        ExchangerateRateResponse mockResponse = ExchangerateRateResponse.builder()
                .base(source)
                .success(true)
                .rates(expectedRates)
                .build();

        //AND
        when(client.getRate(source, target)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(mockResponse));

        //WHEN
        FailedToRetrieveExchangeRateException ex = assertThrows(FailedToRetrieveExchangeRateException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve exchange rate for EUR -> PLN.", ex.getMessage());
        assertEquals("Response has null rate for PLN", ex.getReason());

    }


    @Test
    void should_throw_exception_when_response_body_is_null() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get200());
        when(client.getRate(source, target)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));

        //WHEN
        FailedToRetrieveExchangeRateException ex = assertThrows(FailedToRetrieveExchangeRateException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve exchange rate for EUR -> PLN.", ex.getMessage());
        assertEquals("Empty response", ex.getReason());

    }



    @Test
    void should_throw_exception_when_source_is_NOT_available() {

        //GIVEN
        String source = "INVALID";
        String target = "PLN";
        
        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get200());

        //WHEN
        CurrencyNotAvailableException ex = assertThrows(CurrencyNotAvailableException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("INVALID is not an available currency", ex.getMessage());

    }

    @Test
    void should_throw_exception_when_target_is_NOT_available() {

        //GIVEN
        String source = "EUR";
        String target = "INVALID";
        
        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get200());

        //WHEN
        CurrencyNotAvailableException ex = assertThrows(CurrencyNotAvailableException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("INVALID is not an available currency", ex.getMessage());

    }

    @Test
    void should_throw_exception_when_available_currencies_request_does_not_return_2xx() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.get500());

        //WHEN
        FailedToRetrieveAvailableCurrencies ex = assertThrows(FailedToRetrieveAvailableCurrencies.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve available currencies", ex.getMessage());
        assertEquals("500 INTERNAL_SERVER_ERROR", ex.getReason());

    }

    @Test
    void should_throw_exception_when_available_currencies_request_return_empty_body() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        //AND
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateAvailableCurrenciesResponseFixture.emptyBody());

        //WHEN
        FailedToRetrieveAvailableCurrencies ex = assertThrows(FailedToRetrieveAvailableCurrencies.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve available currencies", ex.getMessage());
        assertEquals("Response is empty", ex.getReason());

    }

}