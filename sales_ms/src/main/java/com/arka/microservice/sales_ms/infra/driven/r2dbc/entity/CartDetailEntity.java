package com.arka.microservice.sales_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "cart_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("quantity")
  private Integer quantity;

  @Column("cart_id")
  @MappedCollection(idColumn = "id")
  private Long cartId;

  @Column("product_id")
  private Long productId;

  @Column("subtotal")
  private Float subtotal;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
