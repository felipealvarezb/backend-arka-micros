package com.arka.microservice.stock_ms.infra.driven.r2dbc.repository;

import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.CountryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICountryEntityRepository extends ReactiveCrudRepository<CountryEntity, Long> {
}
