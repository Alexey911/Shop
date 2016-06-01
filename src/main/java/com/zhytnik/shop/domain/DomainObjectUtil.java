package com.zhytnik.shop.domain;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class DomainObjectUtil {

    private static long lastId = 0;

    private DomainObjectUtil() {
    }

    public static Long nextId() {
        return lastId++;
    }
}
