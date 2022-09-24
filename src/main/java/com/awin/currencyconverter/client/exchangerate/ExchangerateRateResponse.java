package com.awin.currencyconverter.client.exchangerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangerateRateResponse {

     private boolean success;
     private String base;
     private Map<String,Double> rates;

}
