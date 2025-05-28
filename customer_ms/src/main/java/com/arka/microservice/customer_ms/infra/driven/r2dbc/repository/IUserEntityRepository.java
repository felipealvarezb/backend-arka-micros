package com.arka.microservice.customer_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.customer_ms.infra.driven.r2dbc.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IUserEntityRepository extends ReactiveCrudRepository<UserEntity, Long> {
  Mono<UserEntity> findByEmail(String email);
  Mono<UserEntity> findByDni(String dni);
}
