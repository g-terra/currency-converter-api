package com.awin.currencyconverter.client.exception;

import lombok.Getter;

@Getter
public class FailedToRetrieveAvailableCurrencies extends RuntimeException {

    private static final String MESSAGE = "Failed to retrieve available currencies.";

    private final String reason;

    public FailedToRetrieveAvailableCurrencies(String reason) {
        super(MESSAGE);
        this.reason = reason;
    }
}
