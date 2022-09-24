package com.awin.currencyconverter.controller;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.Double.parseDouble;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeProvider currencyExchangeProvider;

    @Test
    void should_convert_EUR_to_USD_with_rate_greater_than_1() throws Exception {

        double amount = 2;
        double rate = 2d;
        double expected = 4;
        String source = "EUR";
        String target = "USD";

        assertCurrencyExchange(amount, rate, expected, source, target);
    }

    @Test
    void should_convert_USD_to_EUR_with_rate_less_than_1() throws Exception {

        double amount = 2;
        double rate = .5d;
        double expected = 1;
        String source = "USD";
        String target = "EUR";

        assertCurrencyExchange(amount, rate, expected, source, target);
    }

    private void assertCurrencyExchange(double amount, double rate, double expected, String source, String target) throws Exception {
        when(currencyExchangeProvider.getRate(source, target)).thenReturn(rate);

        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        new AssertionMatcher<>() {
                            @Override
                            public void assertion(String value) throws AssertionError {
                                assertThat(parseDouble(value)).isEqualTo(expected);
                            }
                        })
                );
    }


}