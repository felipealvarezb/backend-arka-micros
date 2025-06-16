package com.arka.microservice.sales_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.CartEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICartEntityRepository extends ReactiveCrudRepository<CartEntity, Long> {
}
