package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.exeception.ValidationException;

import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class NameValidator implements Validator<String> {

    public static final int MAX_LENGTH = 20;

    @Override
    public void validate(String name) {
        if (name.length() > MAX_LENGTH) {
            failWithLengthExcess(name);
        }
        for (char c : name.toCharArray()) {
            if (!isValid(c)) failWithNotValidCharacter(c);
        }
    }

    // valid values: 'a'-'z', 'A'-'Z', '-', '_'
    private boolean isValid(char c) {
        return (c >= 97 && c <= 122) || (c >= 65 && c <= 90) || c == 95 || c == 46;
    }

    private void failWithLengthExcess(String name) {
        throw new ValidationException(format("Name has length excess %d symbol(-s)", MAX_LENGTH - name.length()));
    }

    private void failWithNotValidCharacter(char c) {
        throw new ValidationException(format("Name contains forbidden symbol \"%s\"", c));
    }
}
