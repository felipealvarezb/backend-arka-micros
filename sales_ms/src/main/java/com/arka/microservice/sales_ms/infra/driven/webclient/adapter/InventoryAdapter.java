package com.arka.microservice.sales_ms.infra.driven.webclient.adapter;

import com.arka.microservice.sales_ms.domain.ports.out.IInventoryOutPort;
import com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto;
import com.arka.microservice.sales_ms.infra.driven.webclient.dto.ProductDto;
import com.arka.microservice.sales_ms.infra.driven.webclient.dto.StockRequestDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryAdapter implements IInventoryOutPort {

  private static final String INVENTORY_SERVICE = "inventoryService";

  @Qualifier("inventoryApiClient")
  private final WebClient webClient;

  @Override
  @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "getProductInventoryFallback")
  @Retry(name = INVENTORY_SERVICE)
  @Bulkhead(name = INVENTORY_SERVICE)
  @TimeLimiter(name = INVENTORY_SERVICE)
  public Mono<InventoryDto> getProductInventory(Long productId) {
    return webClient.get()
            .uri(uriBuilder ->
                    uriBuilder
                            .path("/api/inventory/{id}/inventories")
                            .build(productId)
            )
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<InventoryDto>>() {})
            .flatMapMany(Flux::fromIterable)
            .next();

  }

  @Override
  @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "removeStockFromInventoryFallback")
  @Retry(name = INVENTORY_SERVICE)
  @Bulkhead(name = INVENTORY_SERVICE)
  @TimeLimiter(name = INVENTORY_SERVICE)
  public Mono<String> removeStockFromInventory(Long inventoryId, Long productId, int quantity) {
    return webClient.put()
            .uri(uriBuilder ->
                    uriBuilder
                            .path("/api/inventory/{inventoryId}/product/{productId}/remove/stock")
                            .build(inventoryId, productId)
            )
            .body(BodyInserters.fromValue(new StockRequestDto(quantity)))
            .retrieve()
            .bodyToMono(InventoryDto.class)
            .map(inventory -> String.format("Producto: %s - Cantidad: %s",
                    inventory.getProductId(), inventory.getActualStock()));
  }

  @Override
  @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "addStockToInventoryFallback")
  @Retry(name = INVENTORY_SERVICE)
  @Bulkhead(name = INVENTORY_SERVICE)
  @TimeLimiter(name = INVENTORY_SERVICE)
  public Mono<String> addStockToInventory(Long inventoryId, Long productId, int quantity) {
    return webClient.put()
            .uri(uriBuilder ->
                    uriBuilder
                            .path("/api/inventory/{inventoryId}/product/{productId}/add/stock")
                            .build(inventoryId, productId)
            )
            .body(BodyInserters.fromValue(new StockRequestDto(quantity)))
            .retrieve()
            .bodyToMono(InventoryDto.class)
            .map(inventory -> String.format("Producto: %s - Cantidad: %s",
                    inventory.getProductId(), inventory.getActualStock()));
  }

  @Override
  @CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "getProductByIdFallback")
  @Retry(name = INVENTORY_SERVICE)
  @Bulkhead(name = INVENTORY_SERVICE)
  @TimeLimiter(name = INVENTORY_SERVICE)
  public Mono<ProductDto>   getProductById(Long productId) {
    return webClient.get()
            .uri(uriBuilder ->
                    uriBuilder
                            .path("/api/product/{id}")
                            .build(productId)
            )
            .retrieve()
            .bodyToMono(ProductDto.class);
  }

  private Mono<String> getProductInventoryFallback(Long userId, Exception e) {
    log.warn("Fallback: no se pudo obtener el inventario del producto con ID {}: {}", userId, e.getMessage());
    return Mono.just("No se pudo validar el inventario del producto(fallback).");
  }

  private Mono<String> removeStockFromInventoryFallback(Long inventoryId, Long productId, Exception e) {
    log.warn("Fallback: no se pudo remover el inventario del producto con ID {}: {}", inventoryId, e.getMessage());
    return Mono.just("No se pudo validar el inventario del producto(fallback).");
  }

  private Mono<String> addStockToInventoryFallback(Long inventoryId, Long productId, Exception e) {
    log.warn("Fallback: no se pudo agregar stock al inventario del producto con ID {}: {}", inventoryId, e.getMessage());
    return Mono.just("No se pudo validar el inventario del producto(fallback).");
  }

  private Mono<String> getProductByIdFallback(Long productId, Exception e) {
    log.warn("Fallback: no se puedo obtener el producto con ID {}: {}", productId, e.getMessage());
    return Mono.just("No se pudo validar el inventario del producto(fallback).");
  }
}
