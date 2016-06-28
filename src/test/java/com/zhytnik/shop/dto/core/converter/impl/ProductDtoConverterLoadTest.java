package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.backend.tool.ProductCreator;
import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.testing.PerformanceTest;
import com.zhytnik.shop.util.BenchmarkRunner;
import org.junit.experimental.categories.Category;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.datahelper.ProductDtoDH.createProductDto;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
@Category(PerformanceTest.class)
public class ProductDtoConverterLoadTest extends BenchmarkRunner {

    @State(Scope.Benchmark)
    public static class Parameters {

        static final Long TYPE = 45L;

        ProductDtoConverter converter;
        ProductDto dto;

        @Setup
        public void setUp() {
            converter = new ProductDtoConverter();
            converter.setDynamicDtoConverter(new DynamicDtoConverter());
            converter.setProductCreator(creator());
            converter.setStringDtoConverter(new MultiStringDtoConverter());
            dto = createProductDto();
            dto.setDynamicField("field", 45L);
        }

        private ProductCreator creator() {
            final ProductCreator creator = new ProductCreator();

            final DynamicType type = new DynamicType();
            final DynamicField field = new DynamicField();
            field.setOrder(0);
            field.setName("field");
            field.setPrimitiveType(PrimitiveType.LONG);
            type.setFields(newArrayList(field));

            final TypeRepository repository = mock(TypeRepository.class);
            creator.setTypeRepository(repository);
            when(repository.findOne(TYPE)).thenReturn(type);
            return creator;
        }
    }

    @Benchmark
    public void benchmark(Parameters p, Blackhole bh) {
        bh.consume(p.converter.convert(p.dto));
    }
}
