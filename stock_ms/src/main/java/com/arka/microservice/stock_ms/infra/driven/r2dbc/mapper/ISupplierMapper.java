package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.SupplierModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.SupplierEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISupplierMapper {

  SupplierEntity modelToEntity(SupplierModel model);

  SupplierModel entityToModel(SupplierEntity entity);
}
