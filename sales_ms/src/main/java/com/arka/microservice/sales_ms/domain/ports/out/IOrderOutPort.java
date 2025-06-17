package com.arka.microservice.sales_ms.domain.ports.out;

import com.arka.microservice.sales_ms.domain.model.OrderModel;
import reactor.core.publisher.Mono;

public interface IOrderOutPort {
  Mono<OrderModel> save(OrderModel orderModel);
  Mono<OrderModel> findById(Long id);
  Mono<Void> delete(Long id);
}
