package com.arka.microservice.sales_ms.infra.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
  @Id
  @Column("id")
  private Long id;

  @Column("state")
  private String state;

  @Column("total")
  private Double total;

  @Column("user_id")
  private Long userId;

  @Column("order_detail_id")
  private List<Long> orderDetailIds;

  @Column("created_at")
  private Date createdAt;

  @Column("updated_at")
  private Date updatedAt;
}
