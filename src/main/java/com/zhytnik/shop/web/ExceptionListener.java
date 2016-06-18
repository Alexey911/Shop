package com.zhytnik.shop.web;

import com.zhytnik.shop.exception.TranslatableException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author Alexey Zhytnik
 * @since 14.06.2016
 */
@ControllerAdvice
class ExceptionListener {

    private static final Logger logger = Logger.getLogger(ExceptionListener.class);

    @Autowired
    private MessageSource messages;

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage(), e);
        response.setStatus(SC_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TranslatableException.class)
    public void handleExceptions(TranslatableException e, Locale locale,
                                 HttpServletResponse response) throws IOException {
        logger.info(e.getMessage());
        final HttpStatus status = getHttpStatus(e);
        response.setStatus(status.value());
        if (e.getMessage() != null) {
            final String jsonErrorMessage = format("{\"error\":%s}", extractMessage(e, locale));
            response.getWriter().write(jsonErrorMessage);
        }
    }

    private HttpStatus getHttpStatus(TranslatableException e) {
        return e.getClass().getAnnotation(ResponseStatus.class).value();
    }

    private String extractMessage(TranslatableException e, Locale locale) {
        final String message = messages.getMessage(e.getMessage(), e.getArguments(), locale);
        if (e.getMessage().equals(message)) {
            logger.info("There is no translation for " + e.getMessage());
        }
        return message;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = messageSource;
    }
}
