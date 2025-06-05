package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.CountryModel;
import reactor.core.publisher.Mono;

public interface ICountryInPort {

  Mono<CountryModel> createCountry(CountryModel countryModel);
  Mono<CountryModel> updateCountry(Long id, CountryModel countryModel);
}
