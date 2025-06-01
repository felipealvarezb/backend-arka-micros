package com.arka.microservice.customer_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.customer_ms.domain.model.AddressModel;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAddressEntityMapper {

  @Mapping(source = "userId", target = "user.id")
  AddressModel entityToModel(AddressEntity entity);

  @Mapping(source = "user.id", target = "userId")
  AddressEntity modelToEntity(AddressModel model);

  List<AddressModel> entitiesToModels(List<AddressEntity> entities);
  List<AddressEntity> modelsToEntities(List<AddressModel> models);

}
