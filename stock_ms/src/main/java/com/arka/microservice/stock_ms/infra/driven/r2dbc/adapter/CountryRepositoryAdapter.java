package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.CountryModel;
import com.arka.microservice.stock_ms.domain.ports.out.ICountryOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.ICountryMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.ICountryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CountryRepositoryAdapter implements ICountryOutPort {

  private final ICountryEntityRepository countryEntityRepository;
  private final ICountryMapper countryMapper;

  @Override
  public Mono<CountryModel> save(CountryModel countryModel) {
    return countryEntityRepository.save(countryMapper.modelToEntity(countryModel))
            .map(countryMapper::entityToModel);
  }

  @Override
  public Mono<CountryModel> findById(Long id) {
    return countryEntityRepository.findById(id)
            .map(countryMapper::entityToModel);
  }

  @Override
  public Mono<CountryModel> findByName(String name) {
    return countryEntityRepository.findByName(name)
          .map(countryMapper::entityToModel);
  }

  @Override
  public Flux<CountryModel> findAll() {
    return countryEntityRepository.findAll()
            .map(countryMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return countryEntityRepository.deleteById(id);
  }
}
