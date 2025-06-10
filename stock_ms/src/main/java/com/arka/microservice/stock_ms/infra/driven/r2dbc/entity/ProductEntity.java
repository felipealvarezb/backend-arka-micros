package com.arka.microservice.stock_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "catalog_management.products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("sku")
  private String sku;

  @Column("name")
  private String name;

  @Column("description")
  private String description;

  @Column("image_url")
  private String imageUrl;

  @Column("price")
  private Double price;

  @Column("is_active")
  private Boolean isActive;

  @Column("category_id")
  private Long categoryId;

  @Column("brand_id")
  private Long brandId;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
