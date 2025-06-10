package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.SupplierModel;
import com.arka.microservice.stock_ms.domain.ports.out.ISupplierOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.ISupplierMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.ISupplierEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SupplierRepositoryAdapter implements ISupplierOutPort {

  private final ISupplierEntityRepository supplierEntityRepository;
  private final ISupplierMapper supplierMapper;

  @Override
  public Mono<SupplierModel> save(SupplierModel supplierModel) {
    return supplierEntityRepository.save(supplierMapper.modelToEntity(supplierModel))
            .map(supplierMapper::entityToModel);
  }

  @Override
  public Mono<SupplierModel> findByEmail(String email) {
    return supplierEntityRepository.findByEmail(email)
            .map(supplierMapper::entityToModel);
  }

  @Override
  public Mono<SupplierModel> findById(Long id) {
    return supplierEntityRepository.findById(id)
            .map(supplierMapper::entityToModel);
  }

  @Override
  public Flux<SupplierModel> findAll() {
    return supplierEntityRepository.findAll()
            .map(supplierMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return supplierEntityRepository.deleteById(id);
  }
}
