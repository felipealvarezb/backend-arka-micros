package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.SupplierModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISupplierOutPort {
  Mono<SupplierModel> save(SupplierModel supplierModel);
  Mono<SupplierModel> findByEmail(String email);
  Mono<SupplierModel> findById(Long id);
  Flux<SupplierModel> findAll();
  Mono<Void> delete(Long id);
}
