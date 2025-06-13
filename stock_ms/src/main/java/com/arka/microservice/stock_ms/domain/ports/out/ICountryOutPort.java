package com.arka.microservice.stock_ms.domain.ports.out;

import com.arka.microservice.stock_ms.domain.model.CountryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICountryOutPort {
  Mono<CountryModel> save(CountryModel countryModel);
  Mono<CountryModel> findById(Long id);
  Mono<CountryModel> findByName(String name);
  Flux<CountryModel> findAll();
  Mono<Void> delete(Long id);
}
