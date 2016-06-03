package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.exeception.InfrastructureException;
import com.zhytnik.shop.exeception.ValidationException;
import org.springframework.beans.factory.annotation.Required;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.io.Files.readLines;
import static java.lang.String.format;

/**
 * @author Alexey Zhytnik
 * @since 01.06.2016
 */
public class NameValidator implements Validator<String> {

    public static final int MAX_LENGTH = 20;

    private Set<String> keywords;

    @Override
    public void validate(String name) {
        checkReservedWordOverlap(name);

        if (name.length() > MAX_LENGTH) {
            failWithLengthExcess(name);
        }
        for (char c : name.toCharArray()) {
            if (!isValid(c)) failWithNotValidCharacter(c);
        }
    }

    private void checkReservedWordOverlap(String name) {
        if (keywords.contains(name.toLowerCase())) {
            throw new InfrastructureException(format("Using reserved word \"%s\"", name));
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

    @Required
    public void setKeywordsFile(String keywordsFile) {
        keywords = loadKeywords(keywordsFile);
    }

    private Set<String> loadKeywords(String keywordsFile) {
        final Set<String> keywords = newHashSet();
        final List<String> data;
        try {
            data = readLines(new File(keywordsFile), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new InfrastructureException(e);
        }
        for (String rowKeywords : data) {
            for (String keyword : rowKeywords.split(";")) {
                keywords.add(keyword.toLowerCase());
            }
        }
        return keywords;
    }
}
