package com.zhytnik.shop.exeception;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
public class InfrastructureException extends RuntimeException {

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException() {
        super();
    }

    public InfrastructureException(Exception e) {
        super(e);
    }
}
