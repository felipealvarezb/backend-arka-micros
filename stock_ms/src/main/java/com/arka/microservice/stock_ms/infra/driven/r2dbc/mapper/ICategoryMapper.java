package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.CategoryModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

  CategoryEntity modelToEntity(CategoryModel model);

  CategoryModel entityToModel(CategoryEntity entity);
}
