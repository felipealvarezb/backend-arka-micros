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

@Table(name = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("name")
  private String name;

  @Column("logistic_supervisor_id")
  private Long logisticSupervisorId;
  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
