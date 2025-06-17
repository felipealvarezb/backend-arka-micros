package com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.sales_ms.domain.model.CartDetailModel;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.CartDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartDetailMapper {

  CartDetailModel entityToModel(CartDetailEntity cartDetailEntity);

  CartDetailEntity modelToEntity(CartDetailModel cartDetailModel);
}
