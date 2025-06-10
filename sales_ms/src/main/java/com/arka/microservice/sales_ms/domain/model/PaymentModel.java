package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
  private Long id;
  private String paymentMethod;
  private Double total;
  private String status;
  private Long orderId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
