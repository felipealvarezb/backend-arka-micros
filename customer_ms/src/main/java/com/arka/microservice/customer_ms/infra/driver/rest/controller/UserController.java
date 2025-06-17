package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.in.IUserInPort;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.RegisterUserRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.UpdateUserProfileRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.UserProfileDTO;
import com.arka.microservice.customer_ms.infra.driver.rest.mapper.IUserRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Gestión de usuarios")
public class UserController {

  private final IUserInPort userInPort;
  private final IUserRestMapper userMapper;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Register user", description = "Create and save a user")
  public Mono<UserModel> registerUser(@RequestBody RegisterUserRequest request) {
    return userInPort.registerUser(userMapper.dtoToModel(request));
  }

  @GetMapping("/profile")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get user profile", description = "Get user information profile")
  public Mono<UserProfileDTO> getProfile() {
    return userInPort.getUserProfileInfo()
            .map(userMapper::modelToUserProfileDTO);
  }

  @PutMapping("/profile")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update user", description = "Update and save a user")
  public Mono<UserProfileDTO> updateProfile(@RequestBody UpdateUserProfileRequest request) {
    return userInPort.updateUserProfile(userMapper.updateUserProfileRequestToModel(request))
            .map(userMapper::modelToUserProfileDTO);
  }

}
