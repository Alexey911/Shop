package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.domain.DomainObject;
import com.zhytnik.shop.domain.DomainObjectUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityInterceptorTest {

    static final String ENTITY_NAME = DomainObject.class.getCanonicalName();

    @InjectMocks
    EntityInterceptor interceptor = new EntityInterceptor();

    @Mock
    DomainObjectUtil util;

    @Before
    public void setUp() {
        util.resetGenerate();
    }

    @Test
    public void instantiates() {
        interceptor.instantiate(ENTITY_NAME, null, 10L);
    }

    @Test
    public void turnsOffIdGenerationWhenInstantiate() {
        interceptor.instantiate(ENTITY_NAME, null, 10L);
        InOrder inOrder = inOrder(util);
        inOrder.verify(util).setGenerate(false);
        inOrder.verify(util).resetGenerate();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void insertIntoDomainObjectsIdsWhenTurnOnGeneration() {
        final DomainObject entity = (DomainObject) interceptor.instantiate(ENTITY_NAME, null, 10L);
        assertThat(entity.getId()).isEqualTo(10L);
    }
}
