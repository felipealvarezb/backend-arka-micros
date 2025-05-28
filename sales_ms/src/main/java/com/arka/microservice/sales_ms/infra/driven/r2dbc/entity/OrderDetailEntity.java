package com.arka.microservice.sales_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("quantity")
  private Integer quantity;

  @Column("subtotal")
  private Double subtotal;

  @Column("order_id")
  @MappedCollection(idColumn = "id")
  private Long orderId;

  @Column("product_id")
  private Long productId;

  @Column("created_at")
  private Date createdAt;

  @Column("updated_at")
  private Date updatedAt;
}
