package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import com.awin.currencyconverter.client.exception.CurrencyNotAvailableException;
import com.awin.currencyconverter.client.exception.FailedToRetrieveAvailableCurrencies;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse.*;

@Service
@RequiredArgsConstructor
public class ExchangerateCurrencyExchangeProvider implements CurrencyExchangeProvider {

    private final ExchangerateClient exchangerateClient;

    @Override
    public double getRate(String source, String target) {

        validateCurrencies(source, target);

        ResponseEntity<ExchangerateRateResponse> response = exchangerateClient.getRate(source, target);

        return extractTargetRateFromResponse(source, target, response);
    }

    @Override
    public Map<String, String> getCurrencies() {

        Map<String, Symbol> availableCurrencies = getAvailableCurrencies();

        return availableCurrencies.keySet()
                .stream()
                .collect(Collectors.toMap(k -> k, k -> availableCurrencies.get(k).getDescription()));
    }

    private void validateCurrencies(String source, String target) {

        Map<String, Symbol> currencies = getAvailableCurrencies();

        if (!currencies.containsKey(source)) throw new CurrencyNotAvailableException(source);

        if (!currencies.containsKey(target)) throw new CurrencyNotAvailableException(target);
    }

    private Map<String, Symbol> getAvailableCurrencies() {


        ResponseEntity<ExchangerateAvailableCurrenciesResponse> response = exchangerateClient.getAvailableCurrencies();

        if (!response.getStatusCode().is2xxSuccessful())
            throw new FailedToRetrieveAvailableCurrencies(response.getStatusCode().toString());

        ExchangerateAvailableCurrenciesResponse availableCurrencies = response.getBody();

        if (Objects.isNull(availableCurrencies))
            throw new FailedToRetrieveAvailableCurrencies("Response is empty");

        return availableCurrencies.getSymbols();


    }

    private Double extractTargetRateFromResponse(String source, String target, ResponseEntity<ExchangerateRateResponse> response) {

        if (!response.getStatusCode().is2xxSuccessful())
            throw new FailedToRetrieveExchangeRateException(source, target, String.format("Provider server returned :%s", response.getStatusCode()));

        ExchangerateRateResponse rate = response.getBody();

        if (Objects.isNull(rate)) throw new FailedToRetrieveExchangeRateException(source, target, "Empty response");

        Optional<Double> exchange = rate.getRate(target);

        return exchange.orElseThrow(() -> new FailedToRetrieveExchangeRateException(source, target, String.format("Response has null rate for %s", target)));
    }

}
