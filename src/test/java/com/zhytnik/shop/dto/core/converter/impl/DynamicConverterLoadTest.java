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

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.datahelper.MultiStringDH.createString;

/**
 * @author Alexey Zhytnik
 * @since 30.06.2016
 */
@Category(PerformanceTest.class)
public class DynamicConverterLoadTest extends BenchmarkRunner {


    @State(Scope.Benchmark)
    public static class Parameters {
        DynamicConverter converter;
        Object[] values;
        DynamicType type;

        @Setup
        public void setUp() {
            converter = new DynamicConverter();
            type = new DynamicType();
            type.setFields(newArrayList(
                    create(0, PrimitiveType.BOOLEAN),
                    create(1, PrimitiveType.DOUBLE),
                    create(2, PrimitiveType.LONG),
                    create(3, PrimitiveType.DATE),
                    create(4, PrimitiveType.TEXT),
                    create(5, PrimitiveType.STRING)));
            values = new Object[]{true, 456.0d, 78L, new Date(), "simple text", createString()};
        }

        private DynamicField create(int order, PrimitiveType type) {
            final DynamicField field = new DynamicField();
            field.setOrder(order);
            field.setPrimitiveType(type);
            return field;
        }
    }

    @Benchmark
    public void benchmark(Parameters p, Blackhole bh) {
        bh.consume(p.converter.convert(p.values, p.type));
    }

}
