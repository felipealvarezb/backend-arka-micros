package com.arka.microservice.stock_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeResponseDTO {
  private Long id;
  private String name;
  private String description;
}
