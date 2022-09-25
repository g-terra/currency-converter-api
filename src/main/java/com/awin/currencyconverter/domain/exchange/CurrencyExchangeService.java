package com.awin.currencyconverter.domain.exchange;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.awin.currencyconverter.domain.exchange.Currencies.*;
import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.*;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService implements CurrencyExchange {

    private final CurrencyExchangeProvider currencyExchangeProvider;

    @Override
    public Currencies getCurrencies() {

        List<Currency> collect = currencyExchangeProvider.getCurrencies()
                .entrySet()
                .stream()
                .map(e -> new Currency(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return Currencies.builder().availableCurrencies(collect).build();
    }

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

    @Override
    public MultiCurrencyConversion multiConvert(MultiCurrencyConversionDetails details) {

        Map<String, Double> rates = currencyExchangeProvider.getRates(details.getSource(), details.getTargets());

        return MultiCurrencyConversion.builder()
                .source(details.getSource())
                .targets(details.getTargets())
                .amount(details.getAmount())
                .rates(rates)
                .conversions(aggregateConversions(details, rates))
                .build();
    }

    private Map<String, Double> aggregateConversions(MultiCurrencyConversionDetails details, Map<String, Double> rates) {
        return rates.keySet()
                .stream()
                .collect(Collectors.toMap(k -> k, k -> rates.get(k) * details.getAmount()));
    }


}
