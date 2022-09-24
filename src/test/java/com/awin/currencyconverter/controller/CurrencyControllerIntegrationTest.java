package com.awin.currencyconverter.controller;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.Double.parseDouble;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_convert_EUR_to_USD_with_rate_greater_than_1() throws Exception {

        //GIVEN
        double amount = 2;
        String source = "EUR";
        String target = "USD";

        //WHEN+THEN
        this.mockMvc.perform(get(String.format("/currencies/convert?source=%s&target=%s&amount=%s", source, target, amount)))
                .andExpect(status().isOk())
                .andExpect(
                        content().string(new AssertionMatcher<>() {
                            @Override
                            public void assertion(String value) throws AssertionError {
                                assertThat(parseDouble(value)).isNotEqualTo(amount);
                            }
                        }));
    }


}
