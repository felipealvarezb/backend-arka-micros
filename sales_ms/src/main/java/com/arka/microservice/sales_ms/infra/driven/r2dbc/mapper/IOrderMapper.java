package com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.sales_ms.domain.model.OrderModel;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderMapper {

  OrderModel entityToModel(OrderEntity entity);

  OrderEntity modelToEntity(OrderModel model);
}
