package com.awin.currencyconverter.domain.exchange;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.*;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService implements CurrencyExchange {

    private final CurrencyExchangeProvider currencyExchangeProvider;

    @Override
    public CurrencyConversion convert(CurrencyConversionDetails currencyConversionDetails) {
        double rate = currencyExchangeProvider.getRate(
                currencyConversionDetails.getSource(),
                currencyConversionDetails.getTarget()
        );

        return convert(currencyConversionDetails, rate);
    }

    private CurrencyConversion convert(CurrencyConversionDetails currencyConversionDetails, double rate) {
        return CurrencyConversion.builder()
                .amount(currencyConversionDetails.getAmount())
                .target(currencyConversionDetails.getTarget())
                .source(currencyConversionDetails.getSource())
                .rate(rate)
                .converted(rate * currencyConversionDetails.getAmount())
                .build();
    }
}
