package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.AttributeModel;
import com.arka.microservice.stock_ms.domain.ports.out.IAttributeOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IAttributeMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.IAttributeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AttributeRepositoryAdapter implements IAttributeOutPort {

  private final IAttributeEntityRepository attributeEntityRepository;
  private final IAttributeMapper attributeMapper;

  @Override
  public Mono<AttributeModel> save(AttributeModel attributeModel) {
    return attributeEntityRepository.save(attributeMapper.modelToEntity(attributeModel))
            .map(attributeMapper::entityToModel);
  }

  @Override
  public Mono<AttributeModel> findByName(String name) {
    return attributeEntityRepository.findByName(name)
            .map(attributeMapper::entityToModel);
  }

  @Override
  public Mono<AttributeModel> findById(Long id) {
    return attributeEntityRepository.findById(id)
            .map(attributeMapper::entityToModel);
  }

  @Override
  public Flux<AttributeModel> findAll() {
    return attributeEntityRepository.findAll()
            .map(attributeMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return attributeEntityRepository.deleteById(id);
  }
}
