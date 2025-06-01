package com.arka.microservice.customer_ms.infra.driver.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.arka.microservice.customer_ms.infra.driver.rest.util.AddressDtoConstant.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
  @NotBlank(message = ADDRESS_REQUIRED)
  @Size(max = ADDRESS_MAX_LENGTH)
  private String address;

  @NotBlank(message = CITY_REQUIRED)
  @Size(max = CITY_NAME_LENGTH)
  private String city;

  @NotBlank(message = STATE_REQUIRED)
  @Size(max = STATE_NAME_LENGTH)
  private String state;

  @NotBlank(message = COUNTRY_REQUIRED)
  @Size(max = COUNTRY_NAME_LENGTH)
  private String country;

  @NotBlank(message = POSTAL_CODE_REQUIRED)
  @Pattern(regexp = POSTAL_CODE_REGEX, message = POSTAL_CODE_FORMAT_ERROR)
  @Size(max = POSTAL_CODE_MAX_LENGTH)
  private String zipCode;
}
