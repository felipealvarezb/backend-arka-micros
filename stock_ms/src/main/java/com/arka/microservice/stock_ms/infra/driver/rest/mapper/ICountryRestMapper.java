package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.CountryModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.CountryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.CountryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICountryRestMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  CountryModel countryRequestToModel(CountryRequestDTO countryRequestDTO);

  CountryResponseDTO countryModelToResponseDTO(CountryModel countryModel);
}
