package com.arka.microservice.customer_ms.infra.driven.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "user_management.users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @Column("id")
  private Long id;

  @Column("first_name")
  private String firstName;

  @Column("last_name")
  private String lastName;

  @Column("phone")
  private String phone;

  @Column("email")
  private String email;

  @Column("dni")
  private String dni;

  @Column("password")
  private String password;

  @Column("role_id")
  @MappedCollection(idColumn = "id")
  private Long roleId;

  @Column("is_active")
  private Boolean isActive;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
