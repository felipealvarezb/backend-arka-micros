package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.SupplierEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ISupplierEntityRepository extends ReactiveCrudRepository<SupplierEntity, Long>{
  Mono<SupplierEntity> findByEmail(String email);
}
