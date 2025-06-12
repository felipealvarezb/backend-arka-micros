package com.arka.microservice.stock_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeResponseDTO {
  private Long id;
  private Long productId;
  private Long attributeId;
  private String attributeValue;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}