package com.arka.microservice.stock_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeModel {
  private Long id;
  private Long productId;
  private Long attributeId;
  private Date createdAt;
  private Date updatedAt;
}
