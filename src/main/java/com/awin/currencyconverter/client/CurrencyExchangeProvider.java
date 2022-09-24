package com.awin.currencyconverter.client;

public interface CurrencyExchangeProvider {

    double getRate(String source, String target);

}
