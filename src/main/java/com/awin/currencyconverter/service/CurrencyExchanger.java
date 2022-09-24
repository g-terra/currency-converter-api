package com.awin.currencyconverter.service;

public interface CurrencyExchanger {

    double convert(String source, String target, double amount);

}
