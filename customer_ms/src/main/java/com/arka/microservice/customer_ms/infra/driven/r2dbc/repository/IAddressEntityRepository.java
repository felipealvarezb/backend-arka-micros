package com.arka.microservice.customer_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.customer_ms.infra.driven.r2dbc.entity.AddressEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAddressEntityRepository extends ReactiveCrudRepository<AddressEntity, String> {
  Flux<AddressEntity> findAllByUserId(Long userId);

  Mono<AddressEntity> findByIdAndUserId(Long id, Long userId);
}
