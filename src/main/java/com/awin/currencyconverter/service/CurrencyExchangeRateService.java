package com.awin.currencyconverter.service;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TODO: Implementation of this class has to be backed by https://api.exchangerate.host/latest?base=EUR&symbols=AUD,CAD,CHF,CNY,GBP,JPY,USD
 */
@Service
@RequiredArgsConstructor
public class CurrencyExchangeRateService implements CurrencyService {

    private final CurrencyExchangeProvider currencyExchangeProvider;

    @Override
    public double convert(String source, String target, double amount) {

        double rate = currencyExchangeProvider.getRate(source, target);
        return amount*rate;
    }
}
