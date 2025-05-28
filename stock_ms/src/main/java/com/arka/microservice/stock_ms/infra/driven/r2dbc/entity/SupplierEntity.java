package com.arka.microservice.stock_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("name")
  private String name;

  @Column("email")
  private String email;

  @Column("phone")
  private String phone;

  @Column("address")
  private String address;

  @Column("is_active")
  private Boolean isActive;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
