package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.ProductAttributeModel;
import com.arka.microservice.stock_ms.domain.ports.out.IProductAttributeOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IProductAttributeMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.IProductAttributeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductAttributeRepositoryAdapter implements IProductAttributeOutPort {

  private final IProductAttributeEntityRepository productAttributeEntityRepository;
  private final IProductAttributeMapper productAttributeMapper;

  @Override
  public Mono<ProductAttributeModel> save(ProductAttributeModel productModel) {
    return productAttributeEntityRepository.save(productAttributeMapper.modelToEntity(productModel))
      .map(productAttributeMapper::entityToModel);
  }

  @Override
  public Mono<ProductAttributeModel> findById(Long id) {
    return productAttributeEntityRepository.findById(id)
      .map(productAttributeMapper::entityToModel);
  }

  @Override
  public Flux<ProductAttributeModel> findByProductId(Long productId) {
    return productAttributeEntityRepository.findByProductId(productId)
            .map(productAttributeMapper::entityToModel);
  }

  @Override
  public Mono<ProductAttributeModel> findByProductIdAndAttributeId(Long productId, Long attributeId) {
    return productAttributeEntityRepository.findByProductIdAndAttributeId(productId, attributeId)
      .map(productAttributeMapper::entityToModel);
  }

  @Override
  public Flux<ProductAttributeModel> findAll() {
    return productAttributeEntityRepository.findAll().map(productAttributeMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return productAttributeEntityRepository.deleteById(id);
  }
}
