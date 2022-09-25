package com.awin.currencyconverter.api.exchange;

import com.awin.currencyconverter.domain.exchange.CurrencyConversion;
import com.awin.currencyconverter.domain.exchange.CurrencyExchange;
import org.springframework.http.MediaType;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.*;

@RestController
@Validated
public class CurrencyExchangeController {

    private final CurrencyExchange currencyService;

    public CurrencyExchangeController(final CurrencyExchange currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "currencies/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyConversionResponse convert(
            @Valid @NotBlank(message = "source must not be empty") @RequestParam("source") String source,
            @Valid @NotBlank(message = "target must not be empty") @RequestParam("target") String target,
            @Valid @Positive(message = "amount must be a positive number") @RequestParam("amount") double amount
    ) {

        CurrencyConversionDetails conversionDetails = CurrencyConversionDetails.builder()
                .source(source)
                .target(target)
                .amount(amount)
                .build();

        CurrencyConversion currencyConversion = currencyService.convert(conversionDetails);

        return CurrencyConversionResponse.from(currencyConversion);
    }

    //TODO: introduce endpoint for getting available currencies


}
