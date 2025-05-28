package com.arka.microservice.customer_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IRolEntityMapper.class)
public interface IUserEntityMapper {

  @Mapping(source = "roleId", target = "rol.id")
  @Mapping(source = "isActive", target = "isActive")
  UserModel entityToModel(UserEntity entity);

  @Mapping(source = "rol.id", target = "roleId")
  @Mapping(source = "isActive", target = "isActive")
  UserEntity modelToEntity(UserModel model);
}
