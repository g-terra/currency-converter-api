package com.awin.currencyconverter.service;

import org.springframework.stereotype.Service;

/**
 * TODO: Implementation of this class has to be backed by https://api.exchangerate.host/latest?base=EUR&symbols=AUD,CAD,CHF,CNY,GBP,JPY,USD
 */
@Service
public class CurrencyExchangeRateService implements CurrencyService {

    @Override
    public double convert(String source, String target, double amount) {
        return 1;
    }
}
