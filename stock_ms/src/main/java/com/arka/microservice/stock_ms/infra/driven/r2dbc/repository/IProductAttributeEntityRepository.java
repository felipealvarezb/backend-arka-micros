package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.ProductAttributeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductAttributeEntityRepository extends ReactiveCrudRepository<ProductAttributeEntity, Long> {
  Mono<ProductAttributeEntity> findByProductIdAndAttributeId(Long productId, Long attributeId);
  Flux<ProductAttributeEntity> findByProductId(Long productId);
}
