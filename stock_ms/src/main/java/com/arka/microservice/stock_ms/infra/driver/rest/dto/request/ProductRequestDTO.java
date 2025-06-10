package com.arka.microservice.stock_ms.infra.driver.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
  private String name;
  private String description;
  private String imageUrl;
  private Double price;
  private Boolean isActive;
  private Long categoryId;
  private Long brandId;
}
