package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateRateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangerateClientIntegrationTest {

    @Autowired
    private ExchangerateClient exchangerateClient;

    @Test
    void should_receive_exchange_rate_from_Exchangerate_api() {

        //GIVEN
        String source ="EUR";
        String target ="PLN";

        //WHEN
        ResponseEntity<ExchangerateRateResponse> response = exchangerateClient.getRate(source, target);

        //THEN
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        ExchangerateRateResponse rate = response.getBody();

        assertTrue(rate.isSuccess());
        assertEquals(source, rate.getBase());
        assertEquals(1, rate.getRates().size());
        assertTrue(rate.getRates().containsKey(target));

    }


    @Test
    void should_receive_list_of_all_available_currencies_from_Exchangerate_api() {


        //WHEN
        ResponseEntity<ExchangerateAvailableCurrenciesResponse> response = exchangerateClient.getAvailableCurrencies();

        //THEN
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        ExchangerateAvailableCurrenciesResponse availableCurrencies = response.getBody();

        assertTrue(availableCurrencies.isSuccess());
        assertTrue(availableCurrencies.getSymbols().keySet().size()>0);

    }
}