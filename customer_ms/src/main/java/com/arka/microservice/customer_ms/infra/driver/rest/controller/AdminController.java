package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.in.IUserInPort;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.RegisterUserRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.UserProfileDTO;
import com.arka.microservice.customer_ms.infra.driver.rest.mapper.IUserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final IUserInPort userInPort;
  private final IUserRestMapper userMapper;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<UserModel> registerAdmin(@RequestBody RegisterUserRequest request) {
    return userInPort.registerAdmin(userMapper.dtoToModel(request));
  }

  @GetMapping("/user-list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<UserProfileDTO> getUserList(@RequestParam Optional<String> email,
                                          @RequestParam Optional<String> dni,
                                          @RequestParam Optional<String> name,
                                          @RequestParam(defaultValue = "0") int page) {
    return userInPort.listAllUsers(email, dni, name, page).map(userMapper::modelToUserProfileDTO);
  }

}
