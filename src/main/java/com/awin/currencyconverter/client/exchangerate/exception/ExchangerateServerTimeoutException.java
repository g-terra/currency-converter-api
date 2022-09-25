package com.awin.currencyconverter.client.exchangerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class ExchangerateServerTimeoutException extends Exception {
}
