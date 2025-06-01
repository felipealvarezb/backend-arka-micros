package com.arka.microservice.customer_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.customer_ms.domain.model.AddressModel;
import com.arka.microservice.customer_ms.domain.ports.out.IAddressOutPort;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.mapper.IAddressEntityMapper;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.repository.IAddressEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddressRepositoryAdapter implements IAddressOutPort {

  private final IAddressEntityRepository addressEntityRepository;
  private final IAddressEntityMapper addressEntityMapper;

  @Override
  public Mono<AddressModel> save(AddressModel address) {
    return addressEntityRepository
            .save(addressEntityMapper.modelToEntity(address))
            .map(addressEntityMapper::entityToModel);
  }

  @Override
  public Flux<AddressModel> findAllByUserId(Long userId) {
    return addressEntityRepository.findAllByUserId(userId)
            .map(addressEntityMapper::entityToModel);
  }

  @Override
  public Mono<AddressModel> findByIdAndUserId(Long addressId, Long userId) {
    return addressEntityRepository.findByIdAndUserId(addressId, userId)
            .map(addressEntityMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(AddressModel address) {
    return addressEntityRepository.delete(addressEntityMapper.modelToEntity(address));
  }
}
