package com.arka.microservice.stock_ms.domain.ports.out;

import reactor.core.publisher.Mono;

public interface IUserOutPort {

  Mono<String> getUserAdminLogistic(Long userId);
}
