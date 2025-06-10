package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.AttributeModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAttributeInPort {
  Mono<AttributeModel> createAttribute(AttributeModel attributeModel);
  Mono<AttributeModel> updateAttribute(Long id, AttributeModel attributeModel);
  Flux<AttributeModel> getAllAttributes();
  Mono<String> deleteAttribute(Long id);
}
