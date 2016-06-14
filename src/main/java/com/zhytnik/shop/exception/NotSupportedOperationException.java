package com.zhytnik.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NotSupportedOperationException extends TranslatableException {

    public NotSupportedOperationException(String message) {
        super(message);
    }

    public NotSupportedOperationException(String message, Object... arguments) {
        super(message, arguments);
    }
}
