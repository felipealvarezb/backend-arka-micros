package com.arka.microservice.sales_ms.infra.driven.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {
  private Long id;
  private Integer actualStock;
  private Integer minStock;
  private Long productId;
  private Long countryId;
  private Long supplierId;
}