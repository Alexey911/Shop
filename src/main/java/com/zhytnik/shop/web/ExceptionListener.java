package com.zhytnik.shop.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zhytnik.shop.exception.TranslatableException;
import com.zhytnik.shop.exception.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
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

    @ExceptionHandler(ValidationException.class)
    public void handleValidationExceptions(ValidationException e, Locale locale,
                                           HttpServletResponse response) throws IOException {
        log(e);
        response.setStatus(SC_BAD_REQUEST);
        if (e.hasFieldsErrors()) {
            final JsonObject message = new JsonObject();
            final JsonArray errors = new JsonArray();

            for (ObjectError error : e.getErrors()) {
                errors.add(extractMessage(error, locale));
            }
            message.add("errors", errors);

            response.getWriter().write(message.toString());
        } else {
            sendErrorMessage(response, locale, e);
        }
    }

    private String extractMessage(ObjectError error, Locale locale) {
        Object[] arguments = error.getArguments();
        if (arguments.length > 1) {
            arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        } else {
            arguments = new Object[]{};
        }
        return messages.getMessage(error.getCodes()[0], arguments, locale);
    }

    @ExceptionHandler(TranslatableException.class)
    public void handleExceptions(TranslatableException e, Locale locale,
                                 HttpServletResponse response) throws IOException {
        log(e);
        final HttpStatus status = getHttpStatus(e);
        response.setStatus(status.value());
        sendErrorMessage(response, locale, e);
    }

    private void log(TranslatableException e) {
        if (logger.isDebugEnabled()) {
            logger.debug(e.getMessage(), e);
        } else {
            logger.warn(e.getMessage());
        }
    }

    private HttpStatus getHttpStatus(TranslatableException e) {
        return e.getClass().getAnnotation(ResponseStatus.class).value();
    }

    private void sendErrorMessage(HttpServletResponse response, Locale locale,
                                  TranslatableException e) throws IOException {
        if (e.getMessage() == null) return;
        final JsonObject message = new JsonObject();
        message.addProperty("error", extractMessage(e, locale));
        response.getWriter().write(message.toString());
    }

    private String extractMessage(TranslatableException e, Locale locale) {
        final String message = messages.getMessage(e.getMessage(), e.getArguments(), locale);
        if (e.getMessage().equals(message)) {
            logger.warn("There is no translation for " + e.getMessage());
        }
        return message;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = messageSource;
    }
}
