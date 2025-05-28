package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.InventoryModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventoryMapper {

  InventoryEntity modelToEntity(InventoryModel model);

  InventoryModel entityToModel(InventoryEntity entity);
}
