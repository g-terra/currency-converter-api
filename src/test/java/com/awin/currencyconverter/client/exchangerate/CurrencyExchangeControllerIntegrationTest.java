package com.awin.currencyconverter.client.exchangerate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyExchangeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangerateClient exchangerateClient;

    @Test
    void should_convert_EUR_to_PLN() throws Exception {

        //GIVEN
        double amount = 2;
        double rate = 2;
        double expected = 4;
        String source = "EUR";
        String target = "PLN";

        when(exchangerateClient.getRate(source,target)).thenReturn(ExchangerateClientResponseFixture.rateWithStatus200(rate));
        when(exchangerateClient.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.target").value(target))
                .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(source))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(amount))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rate").value(rate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.converted").value(expected));
    }


    @Test
    void should_return_api_error_for_invalid_source () throws Exception {

        //GIVEN
        double amount = 2;
        double rate = 2;
        String source = "INVALID";
        String target = "PLN";

        when(exchangerateClient.getRate(source,target)).thenReturn(ExchangerateClientResponseFixture.rateWithStatus200(rate));
        when(exchangerateClient.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("currency not available"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("\"INVALID\" is not an available currency."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));

    }


    @Test
    void should_return_api_error_for_invalid_target () throws Exception {

        //GIVEN
        double amount = 2;
        double rate = 2;
        String source = "EUR";
        String target = "INVALID";

        when(exchangerateClient.getRate(source,target)).thenReturn(ExchangerateClientResponseFixture.rateWithStatus200(rate));
        when(exchangerateClient.getAvailableCurrencies()).thenReturn(ExchangerateClientResponseFixture.availableCurrencies200());

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("currency not available"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("\"INVALID\" is not an available currency."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));

    }


}
