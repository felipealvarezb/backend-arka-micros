package com.arka.microservice.sales_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.sales_ms.domain.model.CartModel;
import com.arka.microservice.sales_ms.domain.ports.out.ICartOutPort;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper.ICartMapper;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.repository.ICartEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartRepositoryAdapter implements ICartOutPort {

  private final ICartEntityRepository cartEntityRepository;
  private final ICartMapper cartMapper;

  @Override
  public Mono<CartModel> save(CartModel cartModel) {
    return cartEntityRepository.save(cartMapper.modelToEntity(cartModel))
        .map(cartMapper::entityToModel);
  }

  @Override
  public Mono<CartModel> findById(Long id) {
    return cartEntityRepository.findById(id)
        .map(cartMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return cartEntityRepository.deleteById(id);
  }
}
