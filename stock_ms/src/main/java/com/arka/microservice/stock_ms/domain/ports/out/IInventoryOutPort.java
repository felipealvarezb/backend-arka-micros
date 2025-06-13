package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.InventoryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IInventoryOutPort {
  Mono<InventoryModel> save(InventoryModel inventoryModel);
  Mono<InventoryModel> findById(Long id);
  Flux<InventoryModel> findByProductId(Long productId);
  Flux<InventoryModel> findAll();
  Mono<Void> delete(Long id);
}
