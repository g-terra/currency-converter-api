package com.awin.currencyconverter.api.exchange;

import com.awin.currencyconverter.domain.exchange.Currencies;
import com.awin.currencyconverter.domain.exchange.CurrencyConversion;
import com.awin.currencyconverter.domain.exchange.CurrencyExchange;
import com.awin.currencyconverter.domain.exchange.MultiCurrencyConversion;
import org.springframework.http.MediaType;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import java.util.List;

import static com.awin.currencyconverter.domain.exchange.CurrencyExchangeServiceRequests.*;

@RestController
@Validated
@RequestMapping("currencies")
public class CurrencyExchangeController {

    private final CurrencyExchange currencyService;

    public CurrencyExchangeController(final CurrencyExchange currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrenciesResponse getCurrencies() {

        Currencies currencies = currencyService.getCurrencies();

        return CurrenciesResponse.from(currencies);
    }

    @GetMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/multi-convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public MultiCurrencyConversionResponse multiConvert(
            @Valid @NotBlank(message = "source must not be empty") @RequestParam("source") String source,
            @Valid @NotEmpty(message = "target must not be empty") @RequestParam("target") String[] targets,
            @Valid @Positive(message = "amount must be a positive number") @RequestParam("amount") double amount
    ) {

        MultiCurrencyConversionDetails conversionDetails = MultiCurrencyConversionDetails.builder()
                .source(source)
                .targets(List.of(targets))
                .amount(amount)
                .build();

        MultiCurrencyConversion multiCurrencyConversion = currencyService.multiConvert(conversionDetails);

        return MultiCurrencyConversionResponse.from(multiCurrencyConversion);
    }





}
