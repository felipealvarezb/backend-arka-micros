package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
  private Long id;
  private String state;
  private Double total;
  private List<OrderDetailModel> orderDetails;
  private Long userId;
  private Date createdAt;
  private Date updatedAt;
}
