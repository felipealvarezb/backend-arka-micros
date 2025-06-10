package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.ProductModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductInPort {
  Mono<ProductModel> createProduct(ProductModel productModel);
  Mono<ProductModel> updateProduct(Long id, ProductModel productModel);
  Mono<ProductModel> getProduct(Long id);
  Flux<ProductModel> getAllProducts();
  Mono<String> deleteProduct(Long id);
}
