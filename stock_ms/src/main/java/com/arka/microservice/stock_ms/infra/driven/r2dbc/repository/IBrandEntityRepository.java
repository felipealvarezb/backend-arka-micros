package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.BrandEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IBrandEntityRepository extends ReactiveCrudRepository<BrandEntity, Long> {
  Mono<BrandEntity> findByName(String name);
}
