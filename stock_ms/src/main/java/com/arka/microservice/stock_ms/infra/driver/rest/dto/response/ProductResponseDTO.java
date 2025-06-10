package com.arka.microservice.stock_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
  private String sku;
  private String name;
  private String description;
  private String imageUrl;
  private Double price;
  private Boolean isActive;
  private Long categoryId;
  private Long brandId;
}
