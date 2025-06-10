package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.ProductModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductOutPort {
  Mono<ProductModel> save(ProductModel productModel);
  Mono<ProductModel> findBySku(String sku);
  Mono<ProductModel> findById(Long id);
  Flux<ProductModel> findAll();
  Mono<Void> delete(Long id);
}
