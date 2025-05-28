package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {
  private Long id;
  private Long userId;
  private List<CartDetailModel> cartDetails;
  private String status;
  private Date createdAt;
  private Date updatedAt;
}
