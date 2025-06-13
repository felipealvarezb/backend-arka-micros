package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.exception.NotFoundException;
import com.arka.microservice.stock_ms.domain.exception.ValidationException;
import com.arka.microservice.stock_ms.domain.model.InventoryModel;
import com.arka.microservice.stock_ms.domain.ports.in.IInventoryInPort;
import com.arka.microservice.stock_ms.domain.ports.out.ICountryOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.IInventoryOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.IProductOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.ISupplierOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.CountryConstant.COUNTRY_NOT_FOUND;
import static com.arka.microservice.stock_ms.domain.util.InventoryConstant.*;
import static com.arka.microservice.stock_ms.domain.util.ProductConstant.PRODUCT_NOT_FOUND;
import static com.arka.microservice.stock_ms.domain.util.SupplierConstant.SUPPLIER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class InventoryUseCaseImpl implements IInventoryInPort {

  private final IInventoryOutPort inventoryOutPort;
  private final IProductOutPort productOutPort;
  private final ISupplierOutPort supplierOutPort;
  private final ICountryOutPort countryOutPort;

  @Override
  public Mono<InventoryModel> addInventoryToProduct(Long productId, InventoryModel inventoryModel) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMap(product ->
                    supplierOutPort.findById(inventoryModel.getSupplierId())
                            .switchIfEmpty(Mono.error(new NotFoundException(SUPPLIER_NOT_FOUND)))
                            .flatMap(supplier ->
                                    countryOutPort.findById(inventoryModel.getCountryId())
                                            .switchIfEmpty(Mono.error(new NotFoundException(COUNTRY_NOT_FOUND)))
                                            .flatMap(country -> {
                                              inventoryModel.setProductId(productId);
                                              inventoryModel.setCreatedAt(LocalDateTime.now());
                                              inventoryModel.setUpdatedAt(LocalDateTime.now());
                                              return inventoryOutPort.save(inventoryModel);
                                            })
                            )
            );
  }

  @Override
  public Mono<InventoryModel> updateInventoryForProduct(Long inventoryId, Long productId, InventoryModel inventoryModel) {
    return inventoryOutPort.findById(inventoryId)
            .switchIfEmpty(Mono.error(new NotFoundException(INVENTORY_NOT_FOUND)))
            .flatMap(existingInventory ->
                    productOutPort.findById(productId)
                            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
                            .flatMap(product -> {
                              if (!existingInventory.getProductId().equals(productId)) {
                                return Mono.error(new ValidationException(INVENTORY_MISMATCH));
                              }
                              return supplierOutPort.findById(inventoryModel.getSupplierId())
                                      .switchIfEmpty(Mono.error(new NotFoundException(SUPPLIER_NOT_FOUND)))
                                      .flatMap(supplier ->
                                              countryOutPort.findById(inventoryModel.getCountryId())
                                                      .switchIfEmpty(Mono.error(new NotFoundException(COUNTRY_NOT_FOUND)))
                                                      .flatMap(country -> {
                                                        existingInventory.setMinStock(inventoryModel.getMinStock());
                                                        existingInventory.setActualStock(inventoryModel.getActualStock());
                                                        existingInventory.setCountryId(inventoryModel.getCountryId());
                                                        existingInventory.setSupplierId(inventoryModel.getSupplierId());
                                                        existingInventory.setUpdatedAt(LocalDateTime.now());
                                                        return inventoryOutPort.save(existingInventory);
                                                      })
                                      );
                            })
            );
  }

  @Override
  public Mono<InventoryModel> addStockToProduct(Long productId, Long inventoryId, int quantityToAdd) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMap(product ->
                    inventoryOutPort.findById(inventoryId)
                            .switchIfEmpty(Mono.error(new NotFoundException(INVENTORY_NOT_FOUND)))
                            .flatMap(inventory -> {
                              if (!inventory.getProductId().equals(productId)) {
                                return Mono.error(new ValidationException(INVENTORY_MISMATCH));
                              }
                              inventory.setActualStock(inventory.getActualStock() + quantityToAdd);
                              inventory.setUpdatedAt(LocalDateTime.now());
                              return inventoryOutPort.save(inventory);
                            })
            );
  }

  @Override
  public Mono<InventoryModel> removeStockFromProduct(Long productId, Long inventoryId, int quantityToRemove) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMap(product ->
                    inventoryOutPort.findById(inventoryId)
                            .switchIfEmpty(Mono.error(new NotFoundException(INVENTORY_NOT_FOUND)))
                            .flatMap(inventory -> {
                              if (!inventory.getProductId().equals(productId)) {
                                return Mono.error(new ValidationException(INVENTORY_MISMATCH));
                              }
                              inventory.setActualStock(inventory.getActualStock() - quantityToRemove);
                              inventory.setUpdatedAt(LocalDateTime.now());
                              return inventoryOutPort.save(inventory);
                            })
            );
  }

  @Override
  public Flux<InventoryModel> getInventoriesByProduct(Long productId) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMapMany(product -> inventoryOutPort.findByProductId(productId));
  }
}
