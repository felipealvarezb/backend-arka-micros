package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.BrandModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandMapper {

  BrandEntity modelToEntity(BrandModel model);

  BrandModel entityToModel(BrandEntity entity);
}
