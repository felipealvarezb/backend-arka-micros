package com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.sales_ms.domain.model.CartModel;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartMapper {

  CartEntity modelToEntity(CartModel model);

  CartModel entityToModel(CartEntity entity);



}
