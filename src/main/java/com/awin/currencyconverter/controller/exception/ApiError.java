package com.awin.currencyconverter.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    private final String type;
    private final int status;
    private final String message;

}
