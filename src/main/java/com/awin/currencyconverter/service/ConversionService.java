package com.awin.currencyconverter.service;

public interface ConversionService {

    double convert(String source, String target, double amount);

}
