package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.SupplierModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISupplierInPort {
  Mono<SupplierModel> createSupplier(SupplierModel supplierModel);
  Mono<SupplierModel> updateSupplier(Long id, SupplierModel supplierModel);
  Flux<SupplierModel> getAllSuppliers();
  Mono<String> deleteSupplier(Long id);
}
