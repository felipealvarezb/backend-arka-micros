package com.arka.microservice.customer_ms.infra.driver.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.arka.microservice.customer_ms.infra.driver.security.util.SecurityConstants.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDtoRequest {
  @NotBlank(message = EMAIL_REQUIRED)
  @Email(message = EMAIL_FORMAT_ERROR)
  private String email;

  @NotBlank(message = PASSWORD_REQUIRED)
  private String password;
}
