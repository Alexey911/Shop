package com.zhytnik.shop.exeception;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
