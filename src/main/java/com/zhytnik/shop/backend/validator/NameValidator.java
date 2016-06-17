package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.exception.ValidationException;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Collections.emptySet;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class NameValidator implements Validator<String> {

    public static final int MAX_LENGTH = 20;

    private Set<String> reservedWords = emptySet();

    @Override
    public void validate(String name) {
        if (name == null) {
            throw new ValidationException("Null name");
        }
        checkSpace(name);
        checkReservedWordOverlap(name);

        if (name.length() > MAX_LENGTH) {
            failWithLengthExcess(name);
        }
        for (char c : name.toCharArray()) {
            if (!isValid(c)) failWithNotValidCharacter(c);
        }
    }

    private void checkSpace(String s) {
        if (s.trim().length() < s.length()) {
            throw new ValidationException("string.contains.spaces");
        }
    }

    private void checkReservedWordOverlap(String name) {
        if (reservedWords.contains(name.toLowerCase())) {
            throw new ValidationException("using.reserved.word", name);
        }
    }

    // valid values: 'a'-'z', 'A'-'Z', '1' - '9', '-', '_'
    private boolean isValid(char c) {
        return (c >= 97 && c <= 122) || (c >= 65 && c <= 90) || (c >= 48 && c <= 57) || c == 95 || c == 45;
    }

    private void failWithLengthExcess(String name) {
        throw new ValidationException(format("Name has length excess %d symbol(-s)", MAX_LENGTH - name.length()));
    }

    private void failWithNotValidCharacter(char c) {
        throw new ValidationException(format("Name contains forbidden symbol \"%s\"", c));
    }

    public void setReservedWords(Set<String> reservedWords) {
        this.reservedWords = convertToLowerCase(reservedWords);
    }

    private Set<String> convertToLowerCase(Set<String> strings) {
        final Set<String> lowerCaseStrings = newHashSet();
        for (String s : strings) {
            lowerCaseStrings.add(s.toLowerCase());
        }
        return lowerCaseStrings;
    }
}
