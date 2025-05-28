package com.arka.microservice.customer_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.customer_ms.domain.model.RolModel;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRolEntityMapper {
  RolModel entityToModel(RolEntity entity);

  RolEntity modelToEntity(RolModel model);
}
