package com.arka.microservice.stock_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {
  private Long id;
  private Integer actualStock;
  private Integer minStock;
  private Long productId;
  private Long countryId;
  private Long supplierId;
}
