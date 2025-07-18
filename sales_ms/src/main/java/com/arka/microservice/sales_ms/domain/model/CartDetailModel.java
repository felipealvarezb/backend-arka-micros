package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailModel {
  private Long id;
  private Integer quantity;
  private Long cartId;
  private Long productId;
  private Double subtotal;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
