package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.ProductModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.ProductRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductRestMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  ProductModel  productRequestToModel(ProductRequestDTO productRequestDTO);

  ProductResponseDTO productModelToResponse(ProductModel productModel);

}
