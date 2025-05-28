package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.CountryModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryMapper {

  CountryEntity modelToEntity(CountryModel countryModel);

  CountryModel entityToModel(CountryEntity countryEntity);
}
