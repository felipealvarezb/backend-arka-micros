package com.arka.microservice.sales_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.sales_ms.domain.model.CartDetailModel;
import com.arka.microservice.sales_ms.domain.ports.out.ICartDetailOutPort;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper.ICartDetailMapper;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.repository.ICartDetailEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartDetailRepositoryAdapter implements ICartDetailOutPort {

  private final ICartDetailEntityRepository cartDetailEntityRepository;
  private final ICartDetailMapper cartDetailMapper;


  @Override
  public Mono<CartDetailModel> save(CartDetailModel cartDetailModel) {
    return cartDetailEntityRepository.save(cartDetailMapper.modelToEntity(cartDetailModel))
            .map(cartDetailMapper::entityToModel);
  }

  @Override
  public Mono<CartDetailModel> findByCartIdAndProductId(Long cartId, Long productId) {
    return cartDetailEntityRepository.findByCartIdAndProductId(cartId, productId)
            .map(cartDetailMapper::entityToModel);
  }

  @Override
  public Flux<CartDetailModel> findByCartId(Long cartId) {
    return cartDetailEntityRepository.findByCartId(cartId)
            .map(cartDetailMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return cartDetailEntityRepository.deleteById(id);
  }
}
