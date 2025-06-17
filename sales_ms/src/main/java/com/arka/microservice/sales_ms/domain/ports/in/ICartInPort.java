package com.arka.microservice.sales_ms.domain.ports.in;

import com.arka.microservice.sales_ms.domain.model.CartDetailModel;
import com.arka.microservice.sales_ms.domain.model.CartModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICartInPort {

  Mono<String> createCart(Long userId);
  Mono<String> insertProductToCart(Long cartId, Long productId);
  Mono<String> removeProductFromCart(Long cartId, Long productId);
  Flux<CartDetailModel> showProductsInCart(Long cartId);

}
