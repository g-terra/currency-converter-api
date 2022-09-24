package com.awin.currencyconverter.client.exchangerate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ExchangerateRateResponse {

     private boolean success;
     private String base;
     private Map<String,Double> rates;

     public Optional<Double> getRate(String target){
          return Optional.ofNullable(rates.get(target));
     }

}
