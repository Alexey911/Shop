package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.exeception.ValidationException;

import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class LengthValidator implements Validator<String> {

    public static final int MAX_LENGTH = 255;

    @Override
    public void validate(String s) {
        if (s != null && s.length() <= MAX_LENGTH) {
            failOnExcessStringLength(s);
        }
    }

    private void failOnExcessStringLength(String s) {
        throw new ValidationException(format("String \"%s\" has length excess", s));
    }
}
