package com.zhytnik.shop.util.dataset;

import org.springframework.test.annotation.Commit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
@Commit
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface ExpectedDataSet {
    String value() default "";
}
