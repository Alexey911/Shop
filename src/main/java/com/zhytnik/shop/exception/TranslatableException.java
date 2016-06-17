package com.zhytnik.shop.exception;

/**
 * @author Alexey Zhytnik
 * @since 14.06.2016
 */
public class TranslatableException extends RuntimeException {

    private static final Object[] EMPTY_ARRAY = {};

    protected Object[] arguments;

    protected boolean hasTranslations = true;

    public TranslatableException() {
        arguments = EMPTY_ARRAY;
    }

    public TranslatableException(Throwable cause) {
        super(cause);
        arguments = EMPTY_ARRAY;
    }

    public TranslatableException(String message) {
        super(message);
        arguments = EMPTY_ARRAY;
    }

    public TranslatableException(String message, Object... arguments) {
        super(message);
        this.arguments = arguments;
    }

    public TranslatableException(Throwable cause, String message) {
        super(message, cause);
        this.arguments = EMPTY_ARRAY;
    }

    public TranslatableException(Throwable cause, String message, Object... arguments) {
        super(message, cause);
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public boolean isTranslatable(){
        return hasTranslations;
    }
}
