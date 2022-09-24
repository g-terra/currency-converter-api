package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangerateCurrencyExchangeProvider implements CurrencyExchangeProvider {

    private final ExchangerateClient exchangerateClient;

    @Override
    public double getRate(String source, String target) {
        return 0;
    }

}
