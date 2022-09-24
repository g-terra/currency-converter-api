package com.awin.currencyconverter.controller;

import com.awin.currencyconverter.service.CurrencyExchanger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyController {

    private final CurrencyExchanger currencyService;

    public CurrencyController(final CurrencyExchanger currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "currencies/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public double convert(
            @RequestParam("source") String source,
            @RequestParam("target") String target,
            @RequestParam("amount") double amount) {


        //TODO: fix response to proper json and delegate also handling of errors to controller advise
        return currencyService.convert(source, target, amount);
    }




}
