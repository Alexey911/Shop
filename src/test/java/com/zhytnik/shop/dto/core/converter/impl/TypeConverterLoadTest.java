package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.testing.PerformanceTest;
import com.zhytnik.shop.util.BenchmarkRunner;
import org.junit.experimental.categories.Category;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import static com.zhytnik.shop.datahelper.DynamicTypeDH.createTypeWithSingleField;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
@Category(PerformanceTest.class)
public class TypeConverterLoadTest extends BenchmarkRunner {

    @State(Scope.Benchmark)
    public static class Parameters {
        TypeConverter converter;
        DynamicType type;

        @Setup
        public void setUp() {
            converter = new TypeConverter();
            final FieldConverter fieldConverter = new FieldConverter();
            fieldConverter.setMultiLanguageConverter(new MultiStringConverter());
            converter.setFieldConverter(fieldConverter);
            type = createTypeWithSingleField();
        }
    }

    @Benchmark
    public void benchmark(Parameters p, Blackhole bh) {
        bh.consume(p.converter.convert(p.type));
    }
}
