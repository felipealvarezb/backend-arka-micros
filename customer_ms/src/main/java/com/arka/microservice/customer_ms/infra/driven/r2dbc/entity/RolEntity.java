package com.arka.microservice.customer_ms.infra.driven.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "user_management.roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolEntity {

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
