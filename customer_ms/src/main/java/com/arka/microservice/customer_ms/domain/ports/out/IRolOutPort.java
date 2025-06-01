package com.arka.microservice.customer_ms.domain.ports.out;

import com.arka.microservice.customer_ms.domain.model.RolModel;
import reactor.core.publisher.Mono;

public interface IRolOutPort {
  Mono<RolModel> findByName(String name);
  Mono<RolModel> findById(Long id);
}
