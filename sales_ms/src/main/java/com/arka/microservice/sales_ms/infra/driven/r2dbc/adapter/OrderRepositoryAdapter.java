package com.arka.microservice.sales_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.sales_ms.domain.model.OrderModel;
import com.arka.microservice.sales_ms.domain.ports.out.IOrderOutPort;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper.IOrderMapper;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.repository.IOrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements IOrderOutPort {

  private final IOrderEntityRepository orderEntityRepository;
  private final IOrderMapper orderMapper;

  @Override
  public Mono<OrderModel> save(OrderModel orderModel) {
    return orderEntityRepository.save(orderMapper.modelToEntity(orderModel))
            .map(orderMapper::entityToModel);
  }

  @Override
  public Mono<OrderModel> findById(Long id) {
    return orderEntityRepository.findById(id)
            .map(orderMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return orderEntityRepository.deleteById(id);
  }
}
