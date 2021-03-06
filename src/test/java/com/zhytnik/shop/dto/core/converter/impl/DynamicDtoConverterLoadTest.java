package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.testing.PerformanceTest;
import com.zhytnik.shop.util.BenchmarkRunner;
import org.junit.experimental.categories.Category;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.zhytnik.shop.datahelper.StringDH.createString;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.*;

/**
 * @author Alexey Zhytnik
 * @since 30.06.2016
 */
@Category(PerformanceTest.class)
public class DynamicDtoConverterLoadTest extends BenchmarkRunner {


    @State(Scope.Benchmark)
    public static class Parameters {
        DynamicDtoConverter converter;
        Map<String, Object> values;
        DynamicType type;

        @Setup
        public void setUp() {
            converter = new DynamicDtoConverter();
            type = new DynamicType();
            type.setFields(newArrayList(
                    create(0, BOOLEAN, "boolean_field"),
                    create(1, DOUBLE, "double_field"),
                    create(2, LONG, "long_field"),
                    create(3, DATE, "date_field"),
                    create(4, TEXT, "text_field"),
                    create(5, STRING, "string_field")));
            values = newHashMap();
            values.put("boolean_field", true);
            values.put("double_field", 456.0d);
            values.put("long_field", 35);
            values.put("date_field", new Date().getTime());
            values.put("text_field", "some text");
            values.put("string_field", createString(45L, Locale.CANADA, "string"));
        }

        private DynamicField create(int order, PrimitiveType type, String name) {
            final DynamicField field = new DynamicField();
            field.setOrder(order);
            field.setName(name);
            field.setPrimitiveType(type);
            return field;
        }
    }

    @Benchmark
    public void benchmark(Parameters p, Blackhole bh) {
        bh.consume(p.converter.convert(p.values, p.type));
    }
}
