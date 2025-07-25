package com.arka.microservice.stock_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "catalog_management.brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("name")
  private String name;

  @Column("description")
  private String description;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
