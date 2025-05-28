package com.arka.microservice.customer_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.customer_ms.infra.driven.r2dbc.entity.RolEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IRolEntityRepository extends ReactiveCrudRepository<RolEntity, Long> {
  Mono<RolEntity> findByName(String name);
}
