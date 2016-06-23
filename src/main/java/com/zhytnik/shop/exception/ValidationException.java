package com.zhytnik.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends TranslatableException {

    private List<ObjectError> errors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Object... arguments) {
        super(message, arguments);
    }

    public ValidationException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public boolean hasFieldsErrors() {
        return errors != null && !errors.isEmpty();
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
