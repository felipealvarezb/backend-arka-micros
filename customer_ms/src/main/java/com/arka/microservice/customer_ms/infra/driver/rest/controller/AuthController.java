package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.ports.in.IAuthInPort;
import com.arka.microservice.customer_ms.infra.driver.security.dto.AuthDtoRequest;
import com.arka.microservice.customer_ms.infra.driver.security.dto.AuthDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final IAuthInPort authInPort;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<AuthDtoResponse> login(@RequestBody AuthDtoRequest request) {
    return authInPort
            .authenticate(request.getEmail(), request.getPassword())
            .map(AuthDtoResponse::new);
  }
}
