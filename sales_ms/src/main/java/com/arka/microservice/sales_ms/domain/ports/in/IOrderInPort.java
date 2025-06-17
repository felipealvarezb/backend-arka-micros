package com.arka.microservice.sales_ms.domain.ports.in;

import com.arka.microservice.sales_ms.domain.model.OrderModel;
import reactor.core.publisher.Mono;

public interface IOrderInPort {

  Mono<OrderModel> createOrder(Long userId);
}
