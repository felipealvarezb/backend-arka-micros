package com.arka.microservice.sales_ms.application.usecases;

import com.arka.microservice.sales_ms.domain.model.OrderModel;
import com.arka.microservice.sales_ms.domain.ports.in.IOrderInPort;
import com.arka.microservice.sales_ms.domain.ports.out.IOrderOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderUseCaseImpl implements IOrderInPort {

  private final IOrderOutPort orderOutPort;

  @Override
  public Mono<OrderModel> createOrder(Long userId) {
    return null;
  }
}
