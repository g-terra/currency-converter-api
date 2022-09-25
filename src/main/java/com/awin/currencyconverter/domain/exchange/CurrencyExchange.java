package com.awin.currencyconverter.domain.exchange;

import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.*;

public interface CurrencyExchange {

    CurrencyConversion convert(CurrencyConversionDetails conversionDetails);

    Currencies getCurrencies();
}
