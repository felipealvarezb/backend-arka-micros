package com.arka.microservice.customer_ms.domain.ports.out;

import com.arka.microservice.customer_ms.domain.model.AddressModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAddressOutPort {
  Mono<AddressModel> save(AddressModel address);
  Flux<AddressModel> findAllByUserId(Long userId);
  Mono<AddressModel> findByIdAndUserId(Long addressId, Long userId);
  Mono<Void> delete(AddressModel address);
}
