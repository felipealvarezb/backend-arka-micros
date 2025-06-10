package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.ProductModel;
import com.arka.microservice.stock_ms.domain.ports.out.IProductOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IProductMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.IProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements IProductOutPort {

  private final IProductEntityRepository productEntityRepository;
  private final IProductMapper productMapper;

  @Override
  public Mono<ProductModel> save(ProductModel productModel) {
    return productEntityRepository.save(productMapper.modelToEntity(productModel))
            .map(productMapper::entityToModel);
  }

  @Override
  public Mono<ProductModel> findBySku(String sku) {
    return productEntityRepository.findBySku(sku)
            .map(productMapper::entityToModel);
  }

  @Override
  public Mono<ProductModel> findById(Long id) {
    return productEntityRepository.findById(id)
            .map(productMapper::entityToModel);
  }

  @Override
  public Flux<ProductModel> findAll() {
    return productEntityRepository.findAll()
            .map(productMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return productEntityRepository.deleteById(id);
  }
}
