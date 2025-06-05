package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.CategoryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICategoryOutPort {
  Mono<CategoryModel> save(CategoryModel category);
  Mono<CategoryModel> findByName(String name);
  Mono<CategoryModel> findById(Long id);
  Flux<CategoryModel> findAll();
  Mono<Void> delete(Long id);
}
