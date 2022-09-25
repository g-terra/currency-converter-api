package com.awin.currencyconverter.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class ApiConstraintViolationError extends ApiError {

    private final List<SubErrors> subErrors = new ArrayList<>();

    public ApiConstraintViolationError(int status, String message) {
        super("validation-error", status, message);
    }

    public void addError(String path, String message) {
        subErrors.add(SubErrors.builder().path(path).message(message).build());
    }

    @Getter
    @AllArgsConstructor
    @Builder
    private static class SubErrors {
        private String path;
        private String message;
    }
}