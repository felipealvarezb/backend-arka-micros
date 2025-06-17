package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
  private Long id;
  private String state;
  private Double total;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
