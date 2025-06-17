package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.ports.in.IAuthInPort;
import com.arka.microservice.customer_ms.infra.driver.rest.security.dto.AuthDtoRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.security.dto.AuthDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Gestión de autenticación")
public class AuthController {

  private final IAuthInPort authInPort;

  @Operation(summary = "Login", description = "Authenticate a user")
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<AuthDtoResponse> login(@RequestBody AuthDtoRequest request) {
    return authInPort
            .authenticate(request.getEmail(), request.getPassword())
            .map(AuthDtoResponse::new);
  }
}
