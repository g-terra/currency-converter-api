package com.awin.currencyconverter.client.exchangerate;

import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateAvailableCurrenciesResponse;
import com.awin.currencyconverter.client.exchangerate.responses.ExchangerateRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ExchangerateClient", url = "https://api.exchangerate.host/")
public interface ExchangerateClient {

    @GetMapping(value = "/latest?base={source}&symbols={target}")
    ResponseEntity<ExchangerateRateResponse> getRate(@PathVariable String source, @PathVariable String target);

    @GetMapping(value = "/symbols")
    ResponseEntity<ExchangerateAvailableCurrenciesResponse> getAvailableCurrencies();


}
