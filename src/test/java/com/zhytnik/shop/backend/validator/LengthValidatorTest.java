package com.zhytnik.shop.backend.validator;

import com.zhytnik.shop.testing.UnitTest;
import com.zhytnik.shop.util.StringUtil;
import com.zhytnik.shop.exeception.ValidationException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.zhytnik.shop.backend.validator.LengthValidator.MAX_LENGTH;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@Category(UnitTest.class)
public class LengthValidatorTest {

    LengthValidator validator = new LengthValidator();

    @Test
    public void validates() {
        validator.validate("some string");
    }

    @Test
    public void ignoresNullStrings() {
        validator.validate(null);
    }

    @Test(expected = ValidationException.class)
    public void failsOnExcess() {
        final String excessString = StringUtil.generateByLength(MAX_LENGTH + 1);
        validator.validate(excessString);
    }
}
