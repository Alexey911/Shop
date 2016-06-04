package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.backend.util.StringUtil;
import com.zhytnik.shop.exeception.ValidationException;
import org.junit.Test;

import java.util.Collections;

import static com.zhytnik.shop.backend.validator.NameValidator.MAX_LENGTH;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class NameValidatorTest {

    static final String RESERVED_WORD = "COUNT";

    NameValidator validator = new NameValidator();

    @Test
    public void validates() {
        validator.validate("some_name");
    }

    @Test(expected = ValidationException.class)
    public void failsOnNull() {
        validator.validate(null);
    }

    @Test(expected = ValidationException.class)
    public void failsOnReservedWord() {
        validator.setReservedWords(Collections.singleton(RESERVED_WORD));
        validator.validate(RESERVED_WORD);
    }

    @Test(expected = ValidationException.class)
    public void failsOnExcess() {
        final String excessString = StringUtil.generateByLength(MAX_LENGTH + 1);
        validator.validate(excessString);
    }

    @Test(expected = ValidationException.class)
    public void failsOnForbiddenCharacters() {
        validator.validate("price>10");
    }

    @Test(expected = ValidationException.class)
    public void failsOnSpace() {
        validator.validate("str ");
    }
}
