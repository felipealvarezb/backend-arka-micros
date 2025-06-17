package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {
  private Long id;
  private Long userId;
  private String status;
  private List<CartDetailModel> cartDetails;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
