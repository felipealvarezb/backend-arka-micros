package com.arka.microservice.customer_ms.infra.driver.rest.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.arka.microservice.customer_ms.infra.driver.rest.util.UserDtoConstant.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {
  @NotBlank(message = FIRST_NAME_REQUIRED)
  @Size(max = FIRST_NAME_LENGTH)
  private String firstName;

  @NotBlank(message = LAST_NAME_REQUIRED)
  @Size(max = LAST_NAME_LENGTH)
  private String lastName;

  @NotBlank(message = PHONE_REQUIRED)
  @Size(max = PHONE_LENGTH)
  @Pattern(regexp = PHONE_REGEX, message = PHONE_FORMAT_ERROR)
  private String phone;

  @NotBlank(message = EMAIL_REQUIRED)
  @Email(message = EMAIL_FORMAT_ERROR)
  @Size(max = EMAIL_LENGTH)
  private String email;

  @NotBlank(message = DNI_REQUIRED)
  @Size(max = DNI_LENGTH)
  private String dni;

  @NotBlank(message = PASSWORD_REQUIRED)
  @Size(min = PASSWORD_MIN_LENGTH)
  private String password;
}

