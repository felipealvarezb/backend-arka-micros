package com.arka.microservice.stock_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryModel {
  private Long id;
  private Integer actualStock;
  private Integer minStock;
  private Long productId;
  private Long countryId;
  private Long supplierId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
