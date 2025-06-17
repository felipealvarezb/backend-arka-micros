package com.arka.microservice.sales_ms.domain.ports.out;

import com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto;
import com.arka.microservice.sales_ms.infra.driven.webclient.dto.ProductDto;
import reactor.core.publisher.Mono;

public interface IInventoryOutPort {
  Mono<InventoryDto> getProductInventory(Long productId);
  Mono<String> removeStockFromInventory(Long inventoryId, Long productId, int quantity);
  Mono<String> addStockToInventory(Long inventoryId, Long productId, int quantity);
  Mono<ProductDto> getProductById(Long productId);
}
