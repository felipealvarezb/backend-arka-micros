package com.arka.microservice.stock_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "product_attributes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttribute {
  @Id
  @Column("id")
  private Long id;

  @Column("product_id")
  private Long productId;

  @Column("attribute_id")
  private Long attributeId;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
