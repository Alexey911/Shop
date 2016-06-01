package com.zhytnik.shop.backend.validator;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public interface Validator<T> {
    void validate(T item);
}
