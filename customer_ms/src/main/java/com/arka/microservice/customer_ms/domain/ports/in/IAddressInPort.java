package com.arka.microservice.customer_ms.domain.ports.in;

import com.arka.microservice.customer_ms.domain.model.AddressModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAddressInPort {
  Mono<AddressModel> addAddress(AddressModel addressModel);
  Mono<AddressModel> updateAddress(Long addressId, AddressModel addressModel);
  Flux<AddressModel> listUserAddresses();
  Mono<String> deleteAddress(Long addressId);
}
