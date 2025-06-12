package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.ProductAttributeModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AddProductAttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.ProductAttributeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductAttributeRestMapper {

  @Mapping(target = "productId", ignore = true)
  @Mapping(target = "attributeId", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  ProductAttributeModel requestDtoToModel(AddProductAttributeRequestDTO attributeRequestDTO);

  ProductAttributeResponseDTO modelToResponseDto(ProductAttributeModel productAttributeModel);
}
