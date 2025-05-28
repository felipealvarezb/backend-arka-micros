package com.arka.microservice.stock_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
  private Date createdAt;
  private Date updatedAt;
}
