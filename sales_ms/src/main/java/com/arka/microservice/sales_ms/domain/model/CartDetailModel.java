package com.arka.microservice.sales_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailModel {
  private Long id;
  private Integer quantity;
  private CartModel cart;
  private Long productId;
  private Float subtotal;

  @Column("created_at")
  private Date createdAt;

  @Column("updated_at")
  private Date updatedAt;
}
