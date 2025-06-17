package com.arka.microservice.sales_ms.infra.driver.rest.mapper;

import com.arka.microservice.sales_ms.domain.model.CartDetailModel;
import com.arka.microservice.sales_ms.infra.driver.rest.dto.response.CartDetailResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartDetailRestMapper {

  CartDetailResponseDto modelToResponseDto(CartDetailModel cartDetailModel);
}
