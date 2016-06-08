package com.zhytnik.shop.backend.repository;

import com.zhytnik.shop.domain.market.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 08.06.2016
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findByShortName(String shortName);
}
