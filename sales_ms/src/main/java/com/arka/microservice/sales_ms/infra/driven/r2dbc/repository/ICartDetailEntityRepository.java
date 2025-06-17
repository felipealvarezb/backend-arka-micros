package com.arka.microservice.sales_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.CartDetailEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICartDetailEntityRepository extends ReactiveCrudRepository<CartDetailEntity, Long> {
  Mono<CartDetailEntity> findByCartIdAndProductId(Long cartId, Long productId);
  Flux<CartDetailEntity> findByCartId(Long cartId);
}
