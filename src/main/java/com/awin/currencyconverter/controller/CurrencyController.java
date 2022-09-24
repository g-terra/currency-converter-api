package com.awin.currencyconverter.controller;

import com.awin.currencyconverter.service.ConversionService;
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

    private final ConversionService currencyService;

    public CurrencyController(final ConversionService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "currencies/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public double convert(
            @Valid @NotBlank(message = "source must not be empty") @RequestParam("source") String source,
            @Valid @NotBlank(message = "target must not be empty") @RequestParam("target") String target,
            @Valid @Positive(message = "amount must be a positive number") @RequestParam("amount") double amount
    ) {

        //TODO: change response type to object
        return currencyService.convert(source, target, amount);
    }

    //TODO: introduce endpoint for getting available currencies


}
