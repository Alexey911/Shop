package com.zhytnik.shop.util.dataset;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zhytnik.shop.util.dataset.DropTable.Phase.AFTER;
import static com.zhytnik.shop.util.dataset.DropTable.Phase.BEFORE;

/**
 * @author Alexey Zhytnik
 * @since 15.06.2016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DropTable {
    String[] value() default {};

    Phase[] phases() default {BEFORE, AFTER};

    enum Phase {BEFORE, AFTER}
}
