package com.zhytnik.shop.web;

import com.zhytnik.shop.exception.TranslatableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author Alexey Zhytnik
 * @since 14.06.2016
 */
@ControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private MessageSource messages;

    @ExceptionHandler(Exception.class)
    public void handleExceptions(HttpServletResponse response) throws IOException {
        response.sendError(SC_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TranslatableException.class)
    public void handleExceptions(TranslatableException e, Locale locale,
                                 HttpServletResponse response) throws IOException {
        final HttpStatus status = getHttpStatus(e);
        if (e.getMessage() == null) {
            response.sendError(status.value());
            return;
        }
        final String message = getMessage(e, locale);
        response.sendError(status.value(), message);
    }

    private HttpStatus getHttpStatus(TranslatableException e) {
        return e.getClass().getAnnotation(ResponseStatus.class).value();
    }

    private String getMessage(TranslatableException e, Locale locale) {
        return messages.getMessage(e.getMessage(), e.getArguments(), locale);
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = messageSource;
    }
}
