package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.ProductModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductMapper {

  ProductEntity modelToEntity(ProductModel model);

  ProductModel entityToModel(ProductEntity entity);
}
