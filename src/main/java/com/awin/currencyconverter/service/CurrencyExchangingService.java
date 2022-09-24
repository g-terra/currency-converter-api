package com.awin.currencyconverter.service;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyExchangingService implements CurrencyExchanger {

    private final CurrencyExchangeProvider currencyExchangeProvider;

    @Override
    public double convert(String source, String target, double amount) {
        double rate = currencyExchangeProvider.getRate(source, target);
        return amount*rate;
    }
}
