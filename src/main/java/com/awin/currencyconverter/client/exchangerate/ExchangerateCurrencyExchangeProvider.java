package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import com.awin.currencyconverter.client.exception.CurrencyNotAvailableException;
import com.awin.currencyconverter.client.exception.FailedToRetrieveAvailableCurrencies;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExchangerateCurrencyExchangeProvider implements CurrencyExchangeProvider {

    private final ExchangerateClient exchangerateClient;

    @Override
    public double getRate(String source, String target) {

        return getRates(source,List.of(target)).get(target);
    }

    @Override
    public Map<String, Double> getRates(String source, List<String> targets) {

        log.info("Requesting rate for source:{} and target(s) {}", source, targets);

        validateCurrencies(source, targets);


        ResponseEntity<ExchangerateRateResponse> response = exchangerateClient.getRate(source, StringUtils.join(targets, ","));

        return extractTargetRateFromResponse(source, targets, response);
    }

    @Override
    public Map<String, String> getCurrencies() {


        log.info("Requesting all currencies");


        Map<String, Symbol> availableCurrencies = getAvailableCurrencies();

        return availableCurrencies.keySet()
                .stream()
                .collect(Collectors.toMap(k -> k, k -> availableCurrencies.get(k).getDescription()));
    }

    private void validateCurrencies(String source, List<String> targets) {

        Map<String, Symbol> currencies = getAvailableCurrencies();

        if (!currencies.containsKey(source)) throw new CurrencyNotAvailableException(source);

        Optional<String> invalidCurrency = targets.stream().filter(t -> !currencies.containsKey(t)).findFirst();

        if (invalidCurrency.isPresent()) throw new CurrencyNotAvailableException(invalidCurrency.get());
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

    private Map<String, Double> extractTargetRateFromResponse(String source, List<String> targets, ResponseEntity<ExchangerateRateResponse> response) {

        if (!response.getStatusCode().is2xxSuccessful())
            throw new FailedToRetrieveExchangeRateException(source, StringUtils.join(targets ,',') , String.format("Provider server returned :%s", response.getStatusCode()));

        ExchangerateRateResponse rate = response.getBody();

        if (Objects.isNull(rate)) throw new FailedToRetrieveExchangeRateException(source, StringUtils.join( targets ,','), "Empty response");

        return rate.getRates();
    }

}
