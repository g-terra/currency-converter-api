package com.awin.currencyconverter.client.exchangerate;

import org.assertj.core.util.DateUtil;
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
}