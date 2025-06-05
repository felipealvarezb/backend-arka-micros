package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.BrandModel;
import com.arka.microservice.stock_ms.domain.ports.out.IBrandOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IBrandMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.IBrandEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BrandRepositoryAdapter implements IBrandOutPort {

  private final IBrandEntityRepository brandEntityRepository;
  private final IBrandMapper brandMapper;

  @Override
  public Mono<BrandModel> save(BrandModel brandModel) {
    return brandEntityRepository.save(brandMapper.modelToEntity(brandModel))
            .map(brandMapper::entityToModel);
  }

  @Override
  public Mono<BrandModel> findByName(String name) {
    return brandEntityRepository.findByName(name)
            .map(brandMapper::entityToModel);
  }

  @Override
  public Mono<BrandModel> findById(Long id) {
    return brandEntityRepository.findById(id)
            .map(brandMapper::entityToModel);
  }

  @Override
  public Flux<BrandModel> findAll() {
    return brandEntityRepository.findAll()
            .map(brandMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return brandEntityRepository.deleteById(id);
  }
}
