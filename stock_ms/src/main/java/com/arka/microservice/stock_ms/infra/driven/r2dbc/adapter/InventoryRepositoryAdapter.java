package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.InventoryModel;
import com.arka.microservice.stock_ms.domain.ports.out.IInventoryOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IInventoryMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.IInventoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryRepositoryAdapter implements IInventoryOutPort {

  private final IInventoryEntityRepository inventoryEntityRepository;
  private final IInventoryMapper inventoryMapper;

  @Override
  public Mono<InventoryModel> save(InventoryModel inventoryModel) {
    return inventoryEntityRepository.save(inventoryMapper.modelToEntity(inventoryModel))
            .map(inventoryMapper::entityToModel);
  }

  @Override
  public Mono<InventoryModel> findById(Long id) {
    return inventoryEntityRepository.findById(id)
            .map(inventoryMapper::entityToModel);
  }

  @Override
  public Flux<InventoryModel> findByProductId(Long productId) {
    return inventoryEntityRepository.findByProductId(productId)
            .map(inventoryMapper::entityToModel);
  }

  @Override
  public Flux<InventoryModel> findAll() {
    return inventoryEntityRepository.findAll()
            .map(inventoryMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return inventoryEntityRepository.deleteById(id);
  }
}
