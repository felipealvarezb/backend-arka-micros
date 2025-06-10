package com.arka.microservice.stock_ms.infra.driver.rest.mapper;

import com.arka.microservice.stock_ms.domain.model.SupplierModel;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.SupplierRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.SupplierResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ISupplierRestMapper {

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  SupplierModel  supplierRequestToModel(SupplierRequestDTO supplierRequestDTO);

  SupplierResponseDTO supplierModelToResponseDTO(SupplierModel supplierModel);
}
