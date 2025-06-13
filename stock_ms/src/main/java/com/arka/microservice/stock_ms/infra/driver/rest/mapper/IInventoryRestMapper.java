package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.InventoryModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AddProductInventoryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.InventoryResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventoryRestMapper {

  InventoryModel requestDtoToModel(AddProductInventoryRequestDTO addProductInventoryRequestDTO);

  InventoryResponseDTO modelToResponseDto(InventoryModel inventoryModel);
}
