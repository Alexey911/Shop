package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.exception.ValidationException;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Character.isDigit;
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
        failOnEmpty(name);
        failOnStartsWithDigit(name);
        failOnLengthExcess(name);
        checkSpace(name);
        checkChars(name);
        checkReservedWordOverlap(name);
    }

    private void failOnEmpty(String name) {
        if (name == null || name.isEmpty()) throw new ValidationException("nameValidator.empty.name");
    }

    private void failOnStartsWithDigit(String name) {
        if (isDigit(name.charAt(0))) throw new ValidationException("name.start.with.digit");
    }

    private void failOnLengthExcess(String name) {
        if (name.length() > MAX_LENGTH) {
            final int excess = MAX_LENGTH - name.length();
            throw new ValidationException(format("Name has length excess %d symbol(-s)", excess));
        }
    }

    private void checkSpace(String s) {
        if (s.trim().length() < s.length()) throw new ValidationException("string.contains.spaces");
    }

    private void checkChars(String name) {
        for (char c : name.toCharArray()) {
            if (!isValid(c)) failWithNotValidCharacter(c);
        }
    }

    // valid values: 'a'-'z', 'A'-'Z', '1' - '9', '-', '_'
    private boolean isValid(char c) {
        return (c >= 97 && c <= 122) || (c >= 65 && c <= 90) || (c >= 48 && c <= 57) || c == 95 || c == 45;
    }

    private void failWithNotValidCharacter(char c) {
        throw new ValidationException(format("Name contains forbidden symbol \"%s\"", c));
    }

    private void checkReservedWordOverlap(String name) {
        if (reservedWords.contains(name.toLowerCase())) {
            throw new ValidationException("using.reserved.word", name);
        }
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
