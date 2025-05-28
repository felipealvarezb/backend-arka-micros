package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.AttributeModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.AttributeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAttributeMapper {

  AttributeEntity modelToEntity(AttributeModel model);

  AttributeModel entityToModel(AttributeEntity entity);
}
