package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exception.CurrencyNotAvailableException;
import com.awin.currencyconverter.client.exception.FailedToRetrieveAvailableCurrencies;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());
        when(client.getRate(source,target)).thenReturn(ExchangerateClientResponseFixture.rateWithStatus200(expectedRate));

        //WHEN
        double actualRate = provider.getRate(source, target);

        //THEN
        assertEquals(expectedRate, actualRate);

    }

    @Test
    void should_throw_exception_when_source_is_NOT_available() {

        //GIVEN
        String source = "INVALID";
        String target = "PLN";
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());

        //WHEN
        CurrencyNotAvailableException ex = assertThrows(CurrencyNotAvailableException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("\"INVALID\" is not an available currency.", ex.getMessage());

    }

    @Test
    void should_throw_exception_when_target_is_NOT_available() {

        //GIVEN
        String source = "EUR";
        String target = "INVALID";
        when(client.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());

        //WHEN
        CurrencyNotAvailableException ex = assertThrows(CurrencyNotAvailableException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("\"INVALID\" is not an available currency.", ex.getMessage());

    }


    @Test
    void should_throw_FailedToRetrieveExchangeRateException_when_client_response_is_empty() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        when(client.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());
        when(client.getRate(source, target)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));

        //WHEN
        FailedToRetrieveExchangeRateException ex = assertThrows(FailedToRetrieveExchangeRateException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve exchange rate for EUR -> PLN.", ex.getMessage());
        assertEquals("Empty response", ex.getReason());

    }

    @Test
    void should_throw_FailedToRetrieveAvailableCurrencies_when_available_currencies_request_returns_404() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        when(client.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies404());

        //WHEN
        FailedToRetrieveAvailableCurrencies ex = assertThrows(FailedToRetrieveAvailableCurrencies.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve available currencies.", ex.getMessage());
        assertEquals("404 NOT_FOUND", ex.getReason());
    }


    @Test
    void should_throw_FailedToRetrieveAvailableCurrencies_when_rate_request_returns_404() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        when(client.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());
        when(client.getRate(source,target)).thenReturn(ExchangerateClientResponseFixture.rateWithStatus404());

        //WHEN
        FailedToRetrieveExchangeRateException ex = assertThrows(FailedToRetrieveExchangeRateException.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve exchange rate for EUR -> PLN.", ex.getMessage());
        assertEquals("Provider server returned :404 NOT_FOUND", ex.getReason());
    }

    @Test
    void should_throw_FailedToRetrieveAvailableCurrencies_when_available_currencies_request_is_empty() {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        when(client.getAvailableCurrencies()).thenReturn(ResponseEntity.ok(null));

        //WHEN
        FailedToRetrieveAvailableCurrencies ex = assertThrows(FailedToRetrieveAvailableCurrencies.class, () -> provider.getRate(source, target));

        //THEN
        assertEquals("Failed to retrieve available currencies.", ex.getMessage());
        assertEquals("Response is empty", ex.getReason());
    }




}