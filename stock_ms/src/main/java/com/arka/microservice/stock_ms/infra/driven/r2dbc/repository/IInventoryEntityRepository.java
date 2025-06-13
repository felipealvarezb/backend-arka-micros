package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.InventoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IInventoryEntityRepository extends ReactiveCrudRepository<InventoryEntity, Long> {
  Flux<InventoryEntity> findByProductId(Long productId);
}
