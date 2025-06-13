package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.InventoryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IInventoryInPort {
  Mono<InventoryModel> addInventoryToProduct(Long productId, InventoryModel inventoryModel);
  Mono<InventoryModel> updateInventoryForProduct(Long inventoryId, Long productId, InventoryModel inventoryModel);
  Mono<InventoryModel> addStockToProduct(Long productId, Long inventoryId, int quantityToAdd);
  Mono<InventoryModel> removeStockFromProduct(Long productId, Long inventoryId, int quantityToRemove);
  Flux<InventoryModel> getInventoriesByProduct(Long productId);
}
