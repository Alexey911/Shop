package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.testing.PerformanceTest;
import com.zhytnik.shop.util.BenchmarkRunner;
import org.junit.experimental.categories.Category;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import static com.zhytnik.shop.datahelper.ProductDH.createProduct;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
@Category(PerformanceTest.class)
public class ProductConverterLoadTest extends BenchmarkRunner {

    @State(Scope.Benchmark)
    public static class Parameters {
        ProductConverter converter;
        Product product;

        @Setup
        public void setUp() {
            converter = new ProductConverter();
            converter.setDynamicConverter(new DynamicConverter());
            converter.setStringConverter(new MultiStringConverter());
            product = createProduct();
        }
    }

    @Benchmark
    public void benchmark(Parameters p, Blackhole bh) {
        bh.consume(p.converter.convert(p.product));
    }
}
