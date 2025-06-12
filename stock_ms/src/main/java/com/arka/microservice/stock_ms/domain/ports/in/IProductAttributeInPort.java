package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.ProductAttributeModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductAttributeInPort {
  Mono<ProductAttributeModel> addAttributeToProduct(Long productId, Long attributeId, ProductAttributeModel productAttributeModel);
  Mono<ProductAttributeModel> removeAttributeToProduct(Long productId, Long attributeId, ProductAttributeModel productAttributeModel);
  Mono<ProductAttributeModel> updateAttributeToProduct(Long productId, Long attributeId, ProductAttributeModel productAttributeModel);
  Flux<ProductAttributeModel> getProductAttributes(Long productId);
}
