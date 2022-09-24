package com.awin.currencyconverter.client.exchangerate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

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
    void getRate() {

        ExchangerateRateResponse clientResponse = ExchangerateRateResponse.builder()
                .base("EUR")
                .success(true)
                .rates(Map.of("PLN", 2d))
                .build();

        when(client.getRate("EUR" , "PLN")).thenReturn(ResponseEntity.of(Optional.of(clientResponse)));

        double rate = provider.getRate("EUR", "PLN");

        assertEquals(2,rate);

    }
}