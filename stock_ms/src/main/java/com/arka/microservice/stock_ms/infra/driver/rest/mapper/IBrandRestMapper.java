package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.BrandModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.BrandRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.BrandResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRestMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  BrandModel brandRequestToModel(BrandRequestDTO brandRequestDTO);

  BrandResponseDTO brandModelToResponseDTO(BrandModel brandModel);
}
