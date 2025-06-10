package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.AttributeModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.AttributeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAttributeRestMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  AttributeModel attributeRequestToModel(AttributeRequestDTO attributeRequestDTO);

  AttributeResponseDTO attributeModelToResponseDTO(AttributeModel brandModel);
}
