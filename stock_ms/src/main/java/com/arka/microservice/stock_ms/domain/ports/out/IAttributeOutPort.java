package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.AttributeModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAttributeOutPort {
  Mono<AttributeModel> save(AttributeModel attributeModel);
  Mono<AttributeModel> findByName(String name);
  Mono<AttributeModel> findById(Long id);
  Flux<AttributeModel> findAll();
  Mono<Void> delete(Long id);

}
