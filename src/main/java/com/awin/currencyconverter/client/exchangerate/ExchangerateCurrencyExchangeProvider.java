package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.CurrencyExchangeProvider;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangerateCurrencyExchangeProvider implements CurrencyExchangeProvider {

    private final ExchangerateClient exchangerateClient;

    @Override
    public double getRate(String source, String target) {

        //TODO: implement feature to validate provided source and target against the available Currencies.

        ResponseEntity<ExchangerateRateResponse> response = exchangerateClient.getRate(source, target);

        return extractTargetRateFromResponse(source, target, response);
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
