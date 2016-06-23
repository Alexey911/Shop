package com.zhytnik.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InfrastructureException extends TranslatableException {

    public InfrastructureException() {
        super();
    }

    public InfrastructureException(Throwable cause) {
        super(cause);
    }

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Object... arguments) {
        super(message, arguments);
    }
}
