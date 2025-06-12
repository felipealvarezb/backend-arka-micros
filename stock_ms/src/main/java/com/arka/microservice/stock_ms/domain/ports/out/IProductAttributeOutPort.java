package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.ProductAttributeModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductAttributeOutPort {
  Mono<ProductAttributeModel> save(ProductAttributeModel productModel);
  Mono<ProductAttributeModel> findById(Long id);
  Flux<ProductAttributeModel> findByProductId(Long productId);
  Mono<ProductAttributeModel> findByProductIdAndAttributeId(Long productId, Long attributeId);
  Flux<ProductAttributeModel> findAll();
  Mono<Void> delete(Long id);
}
