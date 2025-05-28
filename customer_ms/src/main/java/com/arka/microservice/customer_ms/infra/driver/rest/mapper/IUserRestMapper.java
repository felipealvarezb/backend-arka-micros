package com.arka.microservice.customer_ms.infra.driver.rest.mapper;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.RegisterUserRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rol", ignore = true)
  @Mapping(target = "isActive", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  UserModel dtoToModel(RegisterUserRequest request);

  UserResponse modelToUserResponse(UserModel model);

}
