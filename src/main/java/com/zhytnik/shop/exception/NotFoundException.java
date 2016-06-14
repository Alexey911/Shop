package com.zhytnik.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends TranslatableException {

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... arguments) {
        super(message, arguments);
    }
}
