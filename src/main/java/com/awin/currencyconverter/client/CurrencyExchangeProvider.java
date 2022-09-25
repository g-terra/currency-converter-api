package com.awin.currencyconverter.client;

import java.util.Map;

public interface CurrencyExchangeProvider {

    double getRate(String source, String target);

    Map<String , String> getCurrencies();

}
