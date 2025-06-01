package com.arka.microservice.customer_ms.infra.driver.rest.mapper;

import com.arka.microservice.customer_ms.domain.model.AddressModel;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.AddressRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.AddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAddressRestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  AddressModel dtoToModel(AddressRequest request);

  AddressResponseDTO modelToAddressResponseDTO(AddressModel model);
}
