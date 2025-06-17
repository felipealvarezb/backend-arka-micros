package com.arka.microservice.customer_ms.domain.ports.out;

import reactor.core.publisher.Mono;

public interface ICartOutPort {

  Mono<String> createCart(Long userId, String jwtToken);
}
