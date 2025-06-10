package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.AttributeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IAttributeEntityRepository extends ReactiveCrudRepository<AttributeEntity, Long>{
  Mono<AttributeEntity> findByName(String name);
}
