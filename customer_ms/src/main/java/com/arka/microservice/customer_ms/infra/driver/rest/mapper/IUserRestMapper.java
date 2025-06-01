package com.arka.microservice.customer_ms.infra.driver.rest.mapper;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.RegisterUserRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.UpdateUserProfileRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.UserProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roleId", ignore = true)
  @Mapping(target = "isActive", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  UserModel dtoToModel(RegisterUserRequest request);

  UserProfileDTO modelToUserProfileDTO(UserModel model);

  UserModel updateUserProfileRequestToModel(UpdateUserProfileRequest request);

}
