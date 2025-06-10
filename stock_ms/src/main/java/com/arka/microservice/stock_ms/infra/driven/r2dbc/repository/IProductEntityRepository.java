package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IProductEntityRepository extends ReactiveCrudRepository<ProductEntity, Long>{
  Mono<ProductEntity> findBySku(String sku);
}
