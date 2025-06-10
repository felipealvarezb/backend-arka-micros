package com.arka.microservice.stock_ms.infra.driver.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequestDTO {
  private String name;
  private String email;
  private String phone;
  private String address;
}
