package com.zhytnik.shop.backend.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.exception.InfrastructureException;
import org.springframework.beans.factory.annotation.Required;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class TypeCache {

    private TypeRepository repository;

    private LoadingCache<Long, DynamicType> cache;

    public TypeCache() {
        initialize();
    }

    public void initialize() {
        cache = CacheBuilder.newBuilder().
                maximumSize(100).
                expireAfterAccess(5, TimeUnit.MINUTES).
                initialCapacity(20).
                build(new CacheLoader<Long, DynamicType>() {
                    @Override
                    public DynamicType load(Long id) throws Exception {
                        return repository.findOne(id);
                    }
                });
    }

    public DynamicType get(Long id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            throw new InfrastructureException(e);
        }
    }

    public void put(DynamicType type) {
        cache.put(type.getId(), type);
    }

    public void refresh(DynamicType type) {
        remove(type);
        put(type);
    }

    public void remove(DynamicType type) {
        cache.invalidate(type.getId());
    }

    @Required
    public void setRepository(TypeRepository repository) {
        this.repository = repository;
    }
}
