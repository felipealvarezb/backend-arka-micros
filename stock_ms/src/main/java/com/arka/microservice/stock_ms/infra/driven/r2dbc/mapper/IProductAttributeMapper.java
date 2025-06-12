package com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.stock_ms.domain.model.ProductAttributeModel;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.entity.ProductAttributeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductAttributeMapper {

  ProductAttributeModel entityToModel(ProductAttributeEntity productAttributeEntity);

  ProductAttributeEntity modelToEntity(ProductAttributeModel productAttributeModel);
}
