package com.awin.currencyconverter.api.exception;

import com.awin.currencyconverter.client.exception.CurrencyNotAvailableException;
import com.awin.currencyconverter.client.exception.FailedToRetrieveAvailableCurrencies;
import com.awin.currencyconverter.client.exception.FailedToRetrieveExchangeRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Global exception handler for API.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final ApiErrorProcessor apiErrorProcessor;

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiError constraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        return apiErrorProcessor.processConstraintViolation(constraintViolations);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return apiErrorProcessor.processConstraintViolation(ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(CurrencyNotAvailableException.class)
    public ApiError currencyNotAvailableException(CurrencyNotAvailableException ex) {
        return apiErrorProcessor.processException("currency not available", BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(FailedToRetrieveExchangeRateException.class)
    public ApiError failedToRetrieveExchangeRateException(FailedToRetrieveExchangeRateException ex) {
        String message = String.format("%sReason: %s", ex.getMessage(), ex.getReason());
        return apiErrorProcessor.processException("Failed to retrieve Exchange rate", BAD_REQUEST, message);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(FailedToRetrieveAvailableCurrencies.class)
    public ApiError failedToRetrieveAvailableCurrencies(FailedToRetrieveAvailableCurrencies ex) {
        String message = String.format("%sReason: %s", ex.getMessage(), ex.getReason());
        return apiErrorProcessor.processException("Failed to retrieve available currencies", BAD_REQUEST, message);
    }

}