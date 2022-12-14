package com.awin.currencyconverter.api.exchange;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import com.awin.currencyconverter.client.exception.CurrencyNotAvailableException;
import com.awin.currencyconverter.client.exception.FailedToRetrieveAvailableCurrencies;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static java.lang.Double.parseDouble;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeProvider currencyExchangeProvider;

    @Test
    void should_convert_EUR_to_USD_with_rate_greater_than_1() throws Exception {

        //GIVEN
        double amount = 2;
        double rate = 2d;
        double expected = 4;
        String source = "EUR";
        String target = "USD";

        //AND
        when(currencyExchangeProvider.getRate(source, target)).thenReturn(rate);

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.target").value(target))
                .andExpect(jsonPath("$.source").value(source))
                .andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.rate").value(rate))
                .andExpect(jsonPath("$.converted").value(expected));

    }

    @Test
    void should_convert_USD_to_EUR_with_rate_less_than_1() throws Exception {

        //GIVEN
        double amount = 2;
        double rate = .5d;
        double expected = 1;
        String source = "USD";
        String target = "EUR";

        //AND
        when(currencyExchangeProvider.getRate(source, target)).thenReturn(rate);

        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.target").value(target))
                .andExpect(jsonPath("$.source").value(source))
                .andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.rate").value(rate))
                .andExpect(jsonPath("$.converted").value(expected));
    }

    @Test
    void should_return_error_when_amount_is_empty() throws Exception {
        //WHEN+THEN
        this.mockMvc.perform(get("/currencies/convert?source=EUR&target=PLN&amount="))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].path").value("amount"))
                .andExpect(jsonPath("$.subErrors[0].message").value("value provided is not the correct type. Expected type: double"));

    }

    @Test
    void should_return_error_when_amount_is_negative() throws Exception {
        //WHEN+THEN
        this.mockMvc.perform(get("/currencies/convert?source=EUR&target=PLN&amount=-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].path").value("convert.amount"))
                .andExpect(jsonPath("$.subErrors[0].message").value("amount must be a positive number"));

    }

    @Test
    void should_return_error_when_amount_is_zero() throws Exception {
        //WHEN+THEN
        this.mockMvc.perform(get("/currencies/convert?source=EUR&target=PLN&amount=0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].path").value("convert.amount"))
                .andExpect(jsonPath("$.subErrors[0].message").value("amount must be a positive number"));

    }

    @Test
    void should_return_error_when_source_is_empty() throws Exception {
        //WHEN+THEN
        this.mockMvc.perform(get("/currencies/convert?source=&target=PLN&amount=1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].path").value("convert.source"))
                .andExpect(jsonPath("$.subErrors[0].message").value("source must not be empty"));

    }

    @Test
    void should_return_error_when_target_is_empty() throws Exception {
        //WHEN+THEN
        this.mockMvc.perform(get("/currencies/convert?source=EUR&target=&amount=1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[0].path").value("convert.target"))
                .andExpect(jsonPath("$.subErrors[0].message").value("target must not be empty"));

    }

    @Test
    void should_return_ApiError_when_currencyExchangeProvider_throws_CurrencyNotAvailableException() throws Exception {

        //GIVEN
        String source = "INVALID";
        String target = "EUR";

        //AND
        when(currencyExchangeProvider.getRate(source, target)).thenThrow(new CurrencyNotAvailableException(source));

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=1", source, target)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("\"INVALID\" is not an available currency."));

    }

    @Test
    void should_return_ApiError_when_currencyExchangeProvider_throws_FailedToRetrieveExchangeRateException() throws Exception {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        //AND
        when(currencyExchangeProvider.getRate(source, target)).thenThrow(new FailedToRetrieveExchangeRateException(source, target, "test"));

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=1", source, target)))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Failed to retrieve exchange rate for EUR -> PLN.Reason: test")
                );

    }

    @Test
    void should_return_ApiError_when_currencyExchangeProvider_throws_FailedToRetrieveAvailableCurrencies() throws Exception {

        //GIVEN
        String source = "EUR";
        String target = "PLN";

        //AND
        when(currencyExchangeProvider.getRate(source, target)).thenThrow(new FailedToRetrieveAvailableCurrencies("test"));

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=1", source, target)))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Failed to retrieve available currencies.Reason: test")
                );

    }

    @Test
    void should_return_ApiError_when_query_parameters_are_missing() throws Exception {

        //WHEN+THEN
        this.mockMvc.perform(get("/currencies/convert"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Required request parameter 'source' for method parameter type String is not present")
                );

        this.mockMvc.perform(get("/currencies/convert?source=test"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Required request parameter 'target' for method parameter type String is not present")
                );


        this.mockMvc.perform(get("/currencies/convert?source=test&target=test"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Required request parameter 'amount' for method parameter type double is not present")
                );


    }

    @Test
    void should_return_object_with_all_available_currencies() throws Exception {

        //WHEN+THEN
        when(currencyExchangeProvider.getCurrencies()).thenReturn(Map.of("EUR", "Euro"));

        this.mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.availableCurrencies").value(hasSize(1))
                ).andExpect(
                        jsonPath("$.availableCurrencies.[0].code").value("EUR")
                ).andExpect(
                        jsonPath("$.availableCurrencies.[0].description").value("Euro")
                );


    }


    @Test
    void should_return_ApiError_when_currencyExchangeProvider_throws_FailedToRetrieveExchangeRateException_on_get_currencies() throws Exception {

        //WHEN+THEN
        when(currencyExchangeProvider.getCurrencies()).thenThrow(new FailedToRetrieveAvailableCurrencies("test"));


        this.mockMvc.perform(get("/currencies"))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value("Failed to retrieve available currencies.Reason: test")
                );


    }


    @Test
    void should_return_ApiError_when_an_unexpected_error_happens() throws Exception {

        //WHEN+THEN
        when(currencyExchangeProvider.getCurrencies()).thenThrow(new RuntimeException("test"));


        this.mockMvc.perform(get("/currencies"))
                .andExpect(status().isInternalServerError())
                .andExpect(
                        jsonPath("$.message")
                                .value("test")
                ).andExpect(
                        jsonPath("$.type")
                                .value("Unexpected error")
                );


    }

}


