package com.example.test.best_travel.infrastructure.abstract_services;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.example.test.best_travel.util.SortType;

public interface CatalogService<R> {
 
    Page<R> readAll(Integer page,Integer size,SortType sortType);

    Set<R> readLessPrice(BigDecimal price);

    Set<R> readBetweenPrice(BigDecimal min,BigDecimal max);

    String FIELD_BY_SORT = "price";
}
