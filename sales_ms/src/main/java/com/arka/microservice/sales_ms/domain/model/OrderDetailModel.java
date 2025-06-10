package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailModel {
  private Long id;
  private Integer quantity;
  private Double subtotal;
  private Long orderId;
  private Long productId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
