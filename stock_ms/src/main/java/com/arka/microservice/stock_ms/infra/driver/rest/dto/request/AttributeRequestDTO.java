package com.arka.microservice.stock_ms.infra.driver.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeRequestDTO {
  private String name;
  private String description;
}
