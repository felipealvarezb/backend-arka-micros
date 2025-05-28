package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailModel {
  private Long id;
  private Integer quantity;
  private Double subtotal;
  private OrderModel order;
  private Long productId;
  private Date createdAt;
  private Date updatedAt;
}
