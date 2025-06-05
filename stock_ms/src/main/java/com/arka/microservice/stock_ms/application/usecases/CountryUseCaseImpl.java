package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.model.CountryModel;
import com.arka.microservice.stock_ms.domain.ports.in.ICountryInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CountryUseCaseImpl implements ICountryInPort {
  @Override
  public Mono<CountryModel> createCountry(CountryModel countryModel) {
    return null;
  }

  @Override
  public Mono<CountryModel> updateCountry(Long id, CountryModel countryModel) {
    return null;
  }
}
