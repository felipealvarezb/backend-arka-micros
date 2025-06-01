package com.arka.microservice.customer_ms.infra.driven.r2dbc.entity;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "user_management.addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

  @Id
  @Column("id")
  private Long id;

  @Column("address")
  private String address;

  @Column("city")
  private String city;

  @Column("state")
  private String state;

  @Column("country")
  private String country;

  @Column("zip_code")
  private String zipCode;

  @Column("user_id")
  private Long userId;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
