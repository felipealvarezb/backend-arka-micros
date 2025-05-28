package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
  private Long id;
  private String paymentMethod;
  private Double total;
  private String status;
  private OrderModel order;
  private Date createdAt;
  private Date updatedAt;
}
