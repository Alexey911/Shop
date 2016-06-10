package com.zhytnik.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.NOT_FOUND;
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
            return new ResponseEntity<>(NOT_FOUND);
        }
        return ok(value);
    }

    public static <T> ResponseEntity<T> badRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
