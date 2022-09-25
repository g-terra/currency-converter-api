package com.awin.currencyconverter.domain.exchange;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.*;
import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.CurrencyConversionDetails.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CurrencyExchangeServiceTest {

    CurrencyExchangeProvider currencyExchangeProvider;

    CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    void setUp() {
        currencyExchangeProvider =  Mockito.mock(CurrencyExchangeProvider.class);

        currencyExchangeService = new CurrencyExchangeService(currencyExchangeProvider);
    }

    @Test
    void should_convert(){
        //GIVEN:
        String source = "EUR";
        String target = "PLN";
        double amount = 2d;
        Double expectedRate = 2d;
        Double expectedConversion = 4d;

        CurrencyConversionDetails details = builder()
                .target(target)
                .amount(amount)
                .source(source)
                .build();

        when(currencyExchangeProvider.getRate(source,target)).thenReturn(expectedRate);

        //WHEN
        CurrencyConversion convert = currencyExchangeService.convert(details);


        //THEN
        assertEquals(expectedConversion , convert.getConverted() );
    }
}