package com.arka.microservice.sales_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.OrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IOrderEntityRepository extends ReactiveCrudRepository<OrderEntity, Long> {
}
