package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.in.IUserInPort;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.RegisterUserRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.UserProfileDTO;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.UserWebClientDTO;
import com.arka.microservice.customer_ms.infra.driver.rest.mapper.IUserRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Gestión de administradores")
public class AdminController {

  private final IUserInPort userInPort;
  private final IUserRestMapper userMapper;

  @Operation(summary = "Register admin", description = "Create and save an Admin")
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UserModel> registerAdmin(@RequestBody RegisterUserRequest request) {
    return userInPort.registerAdmin(userMapper.dtoToModel(request));
  }

  @Operation(summary = "Register admin logistic", description = "Create and save an Admin logistic")
  @PostMapping("/register/admin-logistic")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UserModel> registerAdminLogistic(@RequestBody RegisterUserRequest request) {
    return userInPort.registerAdminLogistic(userMapper.dtoToModel(request));
  }

  @Operation(summary = "Get user list", description = "Get all users with filters")
  @GetMapping("/user-list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<UserProfileDTO> getUserList(@RequestParam Optional<String> email,
                                          @RequestParam Optional<String> dni,
                                          @RequestParam Optional<String> name,
                                          @RequestParam(defaultValue = "0") int page) {
    return userInPort.listAllUsers(email, dni, name, page).map(userMapper::modelToUserProfileDTO);
  }

  @Operation(summary = "Get Admin Logistic", description = "Get an Admin Logistic")
  @GetMapping("/admin-logistic/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserWebClientDTO> getAdminLogistic(@PathVariable Long id) {
    return userInPort.getAdminLogistic(id).map(userMapper::modelToUserWebClientDTO);
  }

}
