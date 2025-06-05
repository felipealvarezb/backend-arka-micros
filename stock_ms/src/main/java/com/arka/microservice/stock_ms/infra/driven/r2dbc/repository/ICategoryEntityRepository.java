package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.CategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ICategoryEntityRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
  Mono<CategoryEntity> findByName(String name);
}
