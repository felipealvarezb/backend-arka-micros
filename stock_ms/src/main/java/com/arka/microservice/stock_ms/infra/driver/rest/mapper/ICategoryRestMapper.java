package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.CategoryModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.CategoryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.CategoryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRestMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  CategoryModel categoryRequestToModel(CategoryRequestDTO categoryRequestDTO);


  CategoryResponseDTO categoryModelToResponseDTO(CategoryModel categoryModel);
}
