package com.zhytnik.shop.backend.dao.query;

import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.testing.UnitTest;
import org.hibernate.SQLQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.shop.backend.dao.query.QueryUtil.*;
import static com.zhytnik.shop.datahelper.ProductDH.createProduct;
import static java.util.Collections.singleton;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryUtilTest {

    @Mock
    SQLQuery query;

    IDynamicEntity entity;

    @Before
    public void setUp() {
        entity = createProduct();
    }

    @Test
    public void fillsQuery() {
        fillQuery(query, entity);
    }

    @Test
    public void fillsQueryByFields() {
        fillQueryByFields(query, entity, singleton(1));
    }

    @Test
    public void setUpTypesTransforms() {
        setResultTransformByType(query, entity.getDynamicType());
    }

    @Test
    public void transformsQueryData() {
        transformQueryData(newArrayList(), 1);
    }
}
