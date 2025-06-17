package com.arka.microservice.sales_ms.domain.ports.out;

import com.arka.microservice.sales_ms.domain.model.CartDetailModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICartDetailOutPort {
  Mono<CartDetailModel> save(CartDetailModel cartDetailModel);
  Mono<CartDetailModel> findByCartIdAndProductId(Long cartId, Long productId);
  Flux<CartDetailModel> findByCartId(Long cartId);
  Mono<Void> delete(Long id);
}
