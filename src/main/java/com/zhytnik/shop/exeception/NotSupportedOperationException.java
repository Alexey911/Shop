package com.zhytnik.shop.exeception;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class NotSupportedOperationException extends RuntimeException {
    public NotSupportedOperationException(String message) {
        super(message);
    }
}
