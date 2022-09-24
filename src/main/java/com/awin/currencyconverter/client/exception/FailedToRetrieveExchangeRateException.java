package com.awin.currencyconverter.client.exception;

import lombok.Getter;

@Getter
public class FailedToRetrieveExchangeRateException extends RuntimeException {

    private static final String MESSAGE = "Failed to retrieve exchange rate for %s -> %s.";

    private final String reason;

    public FailedToRetrieveExchangeRateException(String source, String target, String reason) {
        super(String.format(MESSAGE, source, target));
        this.reason = reason;

    }

}
