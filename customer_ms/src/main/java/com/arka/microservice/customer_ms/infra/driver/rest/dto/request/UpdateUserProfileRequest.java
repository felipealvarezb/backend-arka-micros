package com.arka.microservice.customer_ms.infra.driver.rest.dto.request;

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
public class UpdateUserProfileRequest {
  @NotBlank(message = FIRST_NAME_REQUIRED)
  @Size(max = FIRST_NAME_LENGTH)
  private String firstName;

  @NotBlank(message = LAST_NAME_REQUIRED)
  @Size(max = LAST_NAME_LENGTH)
  private String lastName;

  @NotBlank(message = PHONE_REQUIRED)
  @Pattern(regexp = PHONE_REGEX, message = PHONE_FORMAT_ERROR)
  private String phone;
}
