package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.BrandModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBrandOutPort {
  Mono<BrandModel> save(BrandModel brandModel);
  Mono<BrandModel> findByName(String name);
  Mono<BrandModel> findById(Long id);
  Flux<BrandModel> findAll();
  Mono<Void> delete(Long id);
}
