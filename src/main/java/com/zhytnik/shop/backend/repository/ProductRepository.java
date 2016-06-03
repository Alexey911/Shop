package com.zhytnik.shop.backend.repository;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
@Repository
public interface ProductRepository extends CrudRepository<DynamicType, Long>, JpaSpecificationExecutor<DynamicType> {

    List<DynamicType> findByName(String name);
}
