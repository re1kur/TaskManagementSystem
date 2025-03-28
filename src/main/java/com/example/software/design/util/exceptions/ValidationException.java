package com.example.software.design.util.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<ObjectError> errors;

    public ValidationException(List<ObjectError> errors, String message) {
        super(message);
        this.errors = errors;
    }
}
