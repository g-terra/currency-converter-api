package com.awin.currencyconverter.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@Slf4j
public class ApiErrorProcessor {

    public ApiError processException(String type, HttpStatus status, String message) {
        log.error("{} | {} | {}", type, status, message);
        return new ApiError(type, status.value(), message);
    }

    public ApiError processConstraintViolation(Set<ConstraintViolation<?>> constraintViolations) {
        ApiConstraintViolationError validationError = new ApiConstraintViolationError(BAD_REQUEST.value(), "validation error");
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            validationError.addError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

        log.error("Constraint violation error | {} ", validationError.getSubErrors());

        return validationError;
    }

    public ApiError processConstraintViolation(MethodArgumentTypeMismatchException ex) {
        ApiConstraintViolationError validationError = new ApiConstraintViolationError(BAD_REQUEST.value(), "validation error");
        String message = String.format("value provided is not the correct type. Expected type: %s", ex.getParameter().getParameterType().getSimpleName());
        validationError.addError(ex.getParameter().getParameterName(), message);

        log.error("Constraint violation error | {} ", validationError.getSubErrors());

        return validationError;
    }

}