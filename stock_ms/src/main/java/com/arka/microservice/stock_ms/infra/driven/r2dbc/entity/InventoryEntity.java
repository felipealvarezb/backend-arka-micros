package com.arka.microservice.stock_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "catalog_management.inventories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("actual_stock")
  private Integer actualStock;

  @Column("min_stock")
  private Integer minStock;

  @Column("product_id")
  private Long productId;

  @Column("country_id")
  private Long countryId;

  @Column("supplier_id")
  private Long supplierId;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
