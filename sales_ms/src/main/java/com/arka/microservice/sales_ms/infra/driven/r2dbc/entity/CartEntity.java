package com.arka.microservice.sales_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("status")
  private String status;

  @Column("user_id")
  private Long userId;

  @Column("cart_detail_id")
  private List<Long> cartDetailIds;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
