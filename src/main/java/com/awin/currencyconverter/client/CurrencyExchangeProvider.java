package com.awin.currencyconverter.client;

import java.util.List;
import java.util.Map;

public interface CurrencyExchangeProvider {

    double getRate(String source, String target);

    Map<String,Double> getRates(String source, List<String> target);

    Map<String , String> getCurrencies();

}
