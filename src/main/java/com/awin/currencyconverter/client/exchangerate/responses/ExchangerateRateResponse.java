package com.awin.currencyconverter.client.exchangerate.responses;

import lombok.*;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ExchangerateRateResponse {

     private boolean success;
     private String base;
     @Singular
     private Map<String,Double> rates;

     public Optional<Double> getRate(String target){
          return Optional.ofNullable(rates.get(target));
     }

}
