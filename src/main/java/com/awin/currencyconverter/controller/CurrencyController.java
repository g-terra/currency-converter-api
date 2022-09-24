package com.awin.currencyconverter.controller;

import com.awin.currencyconverter.service.CurrencyExchanger;
import org.springframework.http.MediaType;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@Validated
public class CurrencyController {

    private final CurrencyExchanger currencyService;

    public CurrencyController(final CurrencyExchanger currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "currencies/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public double convert(
            @Valid @NotBlank(message = "source must not be empty") @RequestParam("source") String source,
            @Valid @NotBlank(message = "target must not be empty") @RequestParam("target") String target,
            @Valid @Positive(message = "amount must be a positive number") @RequestParam("amount") double amount
    ) {
        return currencyService.convert(source, target, amount);
    }


}
