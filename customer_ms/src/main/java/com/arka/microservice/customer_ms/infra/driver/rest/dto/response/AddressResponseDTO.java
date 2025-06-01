package com.arka.microservice.customer_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

  private String address;
  private String city;
  private String state;
  private String country;
  private String zipCode;
}
