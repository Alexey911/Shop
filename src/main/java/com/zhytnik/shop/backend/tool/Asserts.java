package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.exeception.NotFoundException;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class Asserts {

    private Asserts() {
    }

    public static void failOnEmpty(Iterable<?> iterable) {
        if (!iterable.iterator().hasNext()) {
            throw new NotFoundException();
        }
    }
}
