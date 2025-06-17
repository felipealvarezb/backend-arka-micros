package com.arka.microservice.sales_ms.domain.ports.out;

import com.arka.microservice.sales_ms.domain.model.CartModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICartOutPort {
  Mono<CartModel> save(CartModel cartModel);
  Mono<CartModel> findById(Long id);
  Mono<Void> delete(Long id);
}
