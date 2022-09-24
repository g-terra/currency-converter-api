package com.awin.currencyconverter.client.exception;

public class CurrencyNotAvailableException extends RuntimeException {

    private static final String MESSAGE = "%s is not an available currency";

    public CurrencyNotAvailableException(String source) {
        super(String.format(MESSAGE, source));

    }

}
