package com.zhytnik.shop.util;

import com.google.common.base.Strings;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class StringUtil {

    private StringUtil() {
    }

    public static String generateByLength(int length) {
        return Strings.padEnd("", length, '*');
    }
}
