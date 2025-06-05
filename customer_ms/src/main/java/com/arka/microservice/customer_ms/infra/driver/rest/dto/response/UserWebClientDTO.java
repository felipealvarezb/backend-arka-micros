package com.arka.microservice.customer_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWebClientDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String dni;
}