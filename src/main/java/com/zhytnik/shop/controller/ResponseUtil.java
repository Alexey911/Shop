package com.zhytnik.shop.controller;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Alexey Zhytnik
 * @since 10.06.2016
 */
class ResponseUtil {

    private ResponseUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseEntity<T> send(T value) {
        if (value == null) {
            return (ResponseEntity<T>) badRequest();
        }
        return ok(value);
    }
}
